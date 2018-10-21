package cn.fintecher.system.service.impl;

import cn.fintecher.common.sms.EntMessageSendDetail;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.client.ManagerFeignUtil;
import cn.fintecher.system.mapper.EntMessageSendDetailMapper;
import cn.fintecher.system.mapper.EntMessageTempletMapper;
import cn.fintecher.system.mapper.SysParaMapper;
import cn.fintecher.system.model.SysPara;
import cn.fintecher.system.service.EntMessageSendDetailService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.StringUtil;
import cn.fintecher.util.redis.RedisKeyConstants;
import cn.fintecher.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月05日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */

@Service
public class EntMessageSendDetailServiceImpl implements EntMessageSendDetailService {

    @Autowired
    private EntMessageSendDetailMapper entMessageSendDetailMapper;

    @Autowired
    private EntMessageTempletMapper entMessageTempletMapper;

    @Autowired
    private SysParaMapper sysParaMapper;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public List<Map> getDetailList(Map map) throws Exception{
        return entMessageSendDetailMapper.findList(map);
    }

    @Override
    public EntMessageSendDetail getDetailById(String id)throws Exception {
        return entMessageSendDetailMapper.selectByPrimaryKey(id);
    }


    @Override
    public Map sendMessage(EntMessageSendDetail entMessageSendDetail) throws Exception{
        Map respMap = new HashMap();
        //判断模板是否存在
        Map param = new HashMap();
        param.put("companyCode",entMessageSendDetail.getCompanyCode());
        param.put("msgType",entMessageSendDetail.getMsgType());
        param.put("status",1);
        List<Map> list = entMessageTempletMapper.findList(param);
        if(list==null||list.size()<1){
            respMap.put("flag",false);
            respMap.put("msg","message.msgTempl.not.existed");
            return respMap;
        }
        String content= String.valueOf(list.get(0).get("content"));
        content = handelMsgContent(entMessageSendDetail,content);
        String isPush= String.valueOf(list.get(0).get("isPush"));//是否极光推送
        String isPrivateMsg= String.valueOf(list.get(0).get("isPrivateMsg"));//是否站内信
        String isSendMsg= String.valueOf(list.get(0).get("isSendMsg"));//是否发送短信
        if (!"null".equals(isSendMsg)&&"1".equals(isSendMsg)){
            //查询短信发送参数
            String account = getParaByCompanyAndKey(entMessageSendDetail.getCompanyCode(), "chuanglan_code_account");
            String password = getParaByCompanyAndKey(entMessageSendDetail.getCompanyCode(), "chuanglan_code_pwd");
            String host = getParaByCompanyAndKey(entMessageSendDetail.getCompanyCode(), "chuanglan_host");
            sendMsg(account,password,host,entMessageSendDetail.getCustomerTel(),content);
        }

        if (!"null".equals(isPush)&&"1".equals(isPush)){
            //查询极光推送系统参数
            String deviceId = entMessageSendDetail.getDeviceId();
            if (deviceId==null||"".equals(deviceId)){
                UserInfo userInfo = UserContextUtil.getUserInfo();
                if (userInfo!=null){
                    deviceId = userInfo.getAppAlias();
                }
            }
            if (deviceId!=null&&!"".equals(deviceId)){
                String masterSecret = getParaByCompanyAndKey(entMessageSendDetail.getCompanyCode(), "jiguang_masterSecret");
                String appKey = getParaByCompanyAndKey(entMessageSendDetail.getCompanyCode(), "jiguang_appKey");
                pushMsg(masterSecret,appKey,deviceId,list.get(0).get("title").toString(),content);
            }
        }

        if (!"null".equals(isPrivateMsg)&&"1".equals(isPrivateMsg)){
            //数据本地持久化
            entMessageSendDetail.setId(UUID.randomUUID().toString());
            entMessageSendDetail.setCreateTime(new Date());
            entMessageSendDetail.setContent(content);
            entMessageSendDetail.setMsgTitle(list.get(0).get("title").toString());
            entMessageSendDetail.setMsgTemplId(list.get(0).get("id").toString());
            entMessageSendDetail.setMsgSendType("1");//站内信
            entMessageSendDetail.setHaveRead((short)1);//是否已读 0 已读 1 未读
            int row =  entMessageSendDetailMapper.insertSelective(entMessageSendDetail);
        }

        respMap.put("flag",true);
        respMap.put("msg","message.send.success");
        return respMap;
    }

    @Override
    public List<EntMessageSendDetail> getMsgTemplListOfApp(Map param) throws Exception {
        return entMessageSendDetailMapper.getMsgTemplListOfApp(param);
    }

    @Override
    public EntMessageSendDetail getMsgTemplDetailOfAppById(String id) throws Exception {
        EntMessageSendDetail messageSendDetail = new EntMessageSendDetail();
        messageSendDetail.setId(id);
        messageSendDetail.setHaveRead((short)0);
        //app查看详情时 消息置未已读 have_read 置0
        entMessageSendDetailMapper.updateByPrimaryKeySelective(messageSendDetail);
        messageSendDetail = entMessageSendDetailMapper.selectByPrimaryKey(id);
        return messageSendDetail;
    }

    /**
    *@Description 处理替换消息内容中的变量值
    *@Param
    *@return
    *@Author coder_bao
    *@Date
    **/
    private String handelMsgContent(EntMessageSendDetail entMessageSendDetail, String content) {
        if (content==null||"".equals(content)){
            return null;
        }
        if (content.contains("${user_name}")){
            content = content.replace("${user_name}",entMessageSendDetail.getCustomerName()==null?"isnull":entMessageSendDetail.getCustomerName().toString());
        }
        if (content.contains("${last_number}")){
            content = content.replace("${last_number}",entMessageSendDetail.getLastNumber()==null?"isnull":entMessageSendDetail.getLastNumber().toString());
        }
        if (content.contains("${month}")&&!content.contains("${year}")){
            String month_day = entMessageSendDetail.getMonthDay();
            if (month_day!=null&&!"".equals(month_day)){
                content = content.replace("${month}",month_day.split("-")[0]);
                content = content.replace("${day}",month_day.split("-")[1]);
            }
        }

        if (content.contains("${year}")){
            Calendar now = Calendar.getInstance();
            content = content.replace("${year}",now.get(Calendar.YEAR)+"");
            content = content.replace("${month}",now.get(Calendar.MONTH) + 1+ "");
            content = content.replace("${day}",now.get(Calendar.DAY_OF_MONTH)+"");
        }

        if (content.contains("${periods_number}")){
            content = content.replace("${periods_number}",entMessageSendDetail.getPeriodsNumber()==null?"isnull":entMessageSendDetail.getPeriodsNumber().toString());
        }
        if (content.contains("${money}")){
            content = content.replace("${money}",entMessageSendDetail.getMoney()==null?"isnull":entMessageSendDetail.getMoney().toString());
        }
        return content;
    }


    /**
     * 发送短信
     * @param acount
     * @param password
     * @param host
     * @param phone
     * @param msg
     * @return
     */
    public Map sendMsg(String acount,String password,String host,String phone,String msg) throws Exception{
        Map respMap = new HashMap();
        //调用 manager服务 发送短信
        Map<String, Object> postParameters = new HashMap<>();
        postParameters.put("account", acount);
        postParameters.put("password", password);
        postParameters.put("host", host);
        postParameters.put("phone", phone);
        postParameters.put("msg", msg);
        ResponseEntity info = restTemplate.postForEntity(ManagerFeignUtil.MANAGER_API_SMS_SENDMESSAGE_API,postParameters,Object.class);
        if (info.getStatusCode().equals(HttpStatus.OK)){
            respMap.put("flag",true);
            respMap.put("msg","message.send.success");
        }else{
            respMap.put("flag",false);
            respMap.put("msg","message.send.fail");
        }
        return respMap;
    }

    /**
     * 极光推送
     * @param masterSecret
     * @param appKey
     * @param deviceId
     * @param title
     * @param message
     * @return
     */
    public Map pushMsg(String masterSecret,String appKey,String deviceId,String title,String message) throws Exception{
        Map respMap = new HashMap();
        //调用manager 极光推送接口
        Map<String, Object> postParameters = new HashMap<>();
        postParameters.put("masterSecret", masterSecret);
        postParameters.put("appKey", appKey);
        postParameters.put("deviceId", deviceId);
        postParameters.put("title",title);
        postParameters.put("message", message);
        ResponseEntity info = restTemplate.postForEntity(ManagerFeignUtil.MANAGER_API_PUSH_JIGUANGPUSH_API,postParameters,Object.class);
        respMap.put("flag",true);
        respMap.put("msg","message.system.save.success");
        return respMap;
    }


    public String getParaByCompanyAndKey(String companyCode, String key)throws Exception{
        String paraValue = "";
        Map sysPara = RedisUtil.get(RedisKeyConstants.SYS_PARA + key + "_" + companyCode);
        if (sysPara == null) {
            Map param = new HashMap();
            param.put("paraName",key);
            param.put("companyCode",companyCode);
            List<SysPara> sysParaList = sysParaMapper.findList(param);
            paraValue = sysParaList.get(0).getParaValue();
        }else{
            paraValue = String.valueOf(sysPara.get("paraValue"));
        }
        return paraValue;
    }

}