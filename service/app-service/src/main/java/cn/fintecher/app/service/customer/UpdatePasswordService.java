package cn.fintecher.app.service.customer;

import cn.fintecher.app.model.customer.EntCustomer;

import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/17
 * @Description:
 */
public interface UpdatePasswordService {

    /**
     * @description: 判断是否实名认证
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/17
     */
    Map isRealnameVerify(Map map) throws Exception;

    /**
     * @description: 修改密码
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/17
     */
    int updatePassword(Map map) throws Exception;

    Map resetPassword(Map map) throws Exception;
}
