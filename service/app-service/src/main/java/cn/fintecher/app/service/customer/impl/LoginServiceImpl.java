package cn.fintecher.app.service.customer.impl;

import cn.fintecher.app.mapper.customer.EntCustomerMapper;
import cn.fintecher.app.model.customer.EntCustomer;
import cn.fintecher.app.service.customer.LoginService;
import cn.fintecher.util.DateUtil;
import cn.fintecher.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/17
 * @Description:
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private EntCustomerMapper customerMapper;

    @Value("${loginErrorCount}")
    private String loginErrorCount;

    @Transactional
    @Override
    public Map login(Map map) throws Exception {
        Map returnMap = new HashMap();
        String password = map.get("password").toString();
        String companyCode = map.get("companyCode").toString();
        //校验用户是否存在
        List<EntCustomer> customerList = customerMapper.selectCustomerByMap(map);
        if (customerList.isEmpty()) {
            returnMap.put("flag", false);
            returnMap.put("msg", "message.customer.not.exist");
            return returnMap;
        }
        //获取用户对象
        EntCustomer cust = customerList.get(0);
        //判断用户状态 状态 0:正常 1:锁定 2:冻结
        int status = cust.getStatus();
        if (status==2){
            returnMap.put("flag", false);
            returnMap.put("msg", "message.customer.frozen");
            return returnMap;
        }
        //如果用户锁定达到24小时，解锁
        if(null != cust.getLockTime()){
            long newTime = new Date().getTime();
            long lockTime = cust.getLockTime().getTime();
            int result = (int)((newTime - lockTime) / (60*60*1000));
            if (result >= 24){
                EntCustomer customer = new EntCustomer();
                customer.setId(cust.getId());
                customer.setErrorCount(0);
                customer.setStatus(0);//解锁
                customer.setLockTime(null);
                customer.setUpdateTime(new Date());
                customerMapper.updateCustomer(customer);
                status = 0;
            }
        }
        if (status==1){
            Date time = cust.getLockTime();
            Calendar c = Calendar.getInstance();
            c.setTime(time);
            c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
            Date tomorrow = c.getTime();
            returnMap.put("flag", false);
            returnMap.put("time", DateUtil.getDateToString(tomorrow,null));
            returnMap.put("msg", "message.customer.error.lock");
            return returnMap;
        }
        //校验密码
        //获取数据里存入的密码
        String loginPassword = cust.getPassword();
        //进行密码比较
        if (!loginPassword.equals(MD5Util.GetMD5Code(password))) {
            //记录密码输入错误次数
            int c = cust.getErrorCount();
            EntCustomer customer = new EntCustomer();
            customer.setId(cust.getId());
            customer.setErrorCount(c+1);
            int errorCount = Integer.valueOf(loginErrorCount);
            if((c+1) == errorCount){
                customer.setStatus(1);//锁定
                customer.setLockTime(new Date());
            }
            customerMapper.updateByPrimaryKeySelective(customer);
            int rc = errorCount - c - 1;
            returnMap.put("flag", false);
            if(rc == 0){
                Calendar ctime = Calendar.getInstance();
                ctime.setTime(new Date());
                ctime.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
                Date tomorrow = ctime.getTime();
                returnMap.put("time", DateUtil.getDateToString(tomorrow,null));
                returnMap.put("msg", "message.customer.error.lock");
            }else {
                returnMap.put("count", rc);
                returnMap.put("msg", "message.password.error.num");
            }
            return returnMap;
        }
        //记录登录时间
        EntCustomer entCustomer = new EntCustomer();
        entCustomer.setId(cust.getId());
        entCustomer.setLoginTime(new Date());
        customerMapper.updateByPrimaryKeySelective(entCustomer);
        returnMap.put("flag",true);
        returnMap.put("msg", "message.login.success");
        returnMap.put("custId", cust.getCustId());
        returnMap.put("companyCode", companyCode);
        returnMap.put("phone",cust.getPhone());
        return returnMap;
    }
}
