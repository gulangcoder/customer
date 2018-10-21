package cn.fintecher.scheduler.task;

import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.model.User;
import cn.fintecher.scheduler.service.SysParaService;
import cn.fintecher.scheduler.service.SystemUserService;
import cn.fintecher.scheduler.service.TaskDispersedLockService;
import cn.fintecher.util.DateUtil;
import cn.fintecher.util.StringUtil;
import cn.fintecher.util.http.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月20日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@Component
@Slf4j
public class OcrAccessTokenTask {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private TaskDispersedLockService taskDispersedLockService;

    @Autowired
    private SysParaService sysParaService;
    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private RedissonClient redissonClient;

    @Value("${define.ocr.grant_type}")
    private String   grant_type ;
    @Value("${define.ocr.version}")
    private String version;
    @Value("${define.ocr.url}")
    private String url;
    @Value("${loginLockUserTime}")
    private String loginLockUserTime;

    @Scheduled(cron="0 0 0/1 * * ?")//0 0/1 * * * ?
    public void reportCurrentTime() {
        // 为了防止死锁和重复执行
        String className = this.getClass().getSimpleName();
        Long utime = null;
        //utime = Long.valueOf(String.valueOf(DateUtil.formatDate(DateUtil.getDateToString(new Date(),null),"yyyyMMddHH")));
        String t = DateUtil.getDateToString(new Date(),"yyyyMMddHH");
        utime = Long.valueOf(t).longValue();
        int num = taskDispersedLockService.updateTask(className,utime);
        if(num == 0) {
            return;
        }
        log.info("更新access_token======================开始");
        //查询企业
        List<Map> companyList = sysParaService.getCompanyCode();
        if (companyList!=null && companyList.size()>0) {
            //对每个企业进行操作
            for (Map map : companyList) {
                if( map.get("companyCode")==null||StringUtil.isEmpty( map.get("companyCode").toString())){
                    continue;
                }
                String companyCode = map.get("companyCode").toString();
                //查找所需要的参数
                String app_id =sysParaService.selectByPrimaryKey("ocr_appid",companyCode);
                String secret =sysParaService.selectByPrimaryKey("ocr_secret",companyCode);
                String access_token = null;
                try {
                    //获取access_token
                    access_token = getAccessToken(url,app_id,secret,grant_type,version);
                    //修改access_token的值
                    int row= sysParaService.updateAccessToken("access_token",access_token,companyCode);
                } catch (Exception e) {
                    log.info("更新access_token："+ ExceptionStackMessage.collectExceptionStackMsg(e));
                }
            }
            log.info("更新access_token======================结束");

        }
    }
    @Scheduled(cron="0 0/30 * * * ?")//0 0/30 * * * ?
    public void handelLockedSystemUser(){
        log.info("解锁system锁定用户======================开始");
        RLock rlock = redissonClient.getLock("unlock_user_redisson_key");
        boolean lockFlag = false;
        //查询所有的被锁定用户
        try {
            //尝试加锁，最多等待20秒，上锁以后10秒自动解锁,可以防出现死锁
            lockFlag = rlock.tryLock(20,10,TimeUnit.SECONDS);
            if (lockFlag){
                List<User> locked_users = systemUserService.getLockedUserList();
                if (locked_users!=null&&locked_users.size()>0){
                    List<String> need_unlock_users = new ArrayList<>();
                    //判断用户是否可以解锁
                    for (User user:locked_users){
                        boolean flag = handelLockedUser(user);
                        if (flag){
                            need_unlock_users.add(user.getUserId());
                        }
                    }
                    //解锁用户
                    systemUserService.unLockUser(need_unlock_users);
                }
            }
        } catch (Exception e) {
            log.info("解锁system锁定用户异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
        }finally {
            if (lockFlag){
                rlock.unlock();
            }
        }
        log.info("解锁system锁定用户======================结束");
    }

    /**
     * @Description
     * @Param
     * @return
     * @Author coder_bao
     * @Date 2018/9/30 13:56
     */
    private boolean handelLockedUser(User user) throws Exception{
        if (user.getLockTime()==null){
            return false;
        }
        if(loginLockUserTime==null||"".equals(loginLockUserTime)){
            return false;
        }
        Map<String,Long> map_time = DateUtil.getMapDistanceTime(DateUtil.getDateToString(user.getLockTime(),null),DateUtil.getCurrentTime());
        String lock_date = DateUtil.minConvertHourMin(Integer.parseInt(loginLockUserTime));
        long lock_day = 0;
        long lock_hour = 0;
        long lock_minute = 0;
        if (lock_date!=null&&!"".equals(lock_date)){
            if (lock_date.contains("天")){
                lock_day = Long.parseLong(lock_date.substring(0,lock_date.indexOf("天")));
            }
            if (lock_date.contains("时")){
                lock_hour = Long.parseLong(lock_date.substring(lock_date.indexOf("天")+1,lock_date.indexOf("时")));
            }
            if (lock_date.contains("分")){
                lock_minute = Long.parseLong(lock_date.substring(lock_date.indexOf("时")+1,lock_date.indexOf("分")));
            }
        }
        //判断锁定时长是否超过系统配置值
        if (map_time!=null&&map_time.size()>0){
            long day = map_time.get("day").longValue();
            long hour = map_time.get("hour").longValue();
            long minute = map_time.get("minute").longValue();
            if (day>lock_day){
                return true;
            }
            if (day<lock_day){
                return false;
            }
            if (hour>lock_hour){
                return true;
            }
            if (hour<lock_hour){
                return false;
            }
            if (minute>=lock_minute){
                return true;
            }
            if (minute<lock_minute){
                return false;
            }
        }
        return false;
    }



    /**
     * 获取access_token
     * @param url
     * @param app_id
     * @param secret
     * @param grant_type
     * @param version
     * @return
     * @throws Exception
     */
    public  String getAccessToken(String url,String app_id,String secret,String grant_type,String version) throws Exception{
        url = url+"?app_id="+app_id+"&secret="+secret+"&grant_type="+grant_type+"&version="+version;
        log.info("发送活体参数getAccessToken---->" + url);
        String data = HttpClientUtil.getInstance().sendHttpGet(url);
        log.info("接收活体参数getAccessToken---->" + data);
        JSONObject jsonObject = JSON.parseObject(data);
        String access_token = jsonObject.getString("access_token");
        return access_token;
    }
}