package cn.fintecher.app.service.customer;

import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/17
 * @Description:
 */
public interface SmsService {

    /**
     * @description: 判断验证码是否正确
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/17
     */
    Map checkSms(Map map) throws Exception;

    /**
     * @description: 发送短信验证码
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/19
     */
    Map sendSmsCode(String phone,String code,String companyCode) throws Exception;
}
