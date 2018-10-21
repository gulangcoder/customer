package cn.fintecher.app.service.customer;

import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/17
 * @Description:
 */
public interface LoginService {

    /**
     * @description: 登录
     * @param: phone，password，companyCode
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/17
     */
    Map login(Map map) throws Exception;
}
