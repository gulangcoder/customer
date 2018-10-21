package cn.fintecher.app.service.customer;

import cn.fintecher.app.model.customer.EntContractTemplet;
import cn.fintecher.app.model.customer.EntCustomer;

import java.util.List;
import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/17
 * @Description:
 */
public interface RegisterService {

    /**
     * @description: 判断手机是否被注册
     * @param:tel,company
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/17
     */
    List<EntCustomer> selectCustomerByMap(Map map) throws Exception;

    /**
     * @description: 保存注册客户
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/17
     */
    Map saveCustomer(Map map) throws Exception;

    /**
     * @description: 获取注册协议，服务协议
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/17
     */
    List<EntContractTemplet> getRegisterProtocol(Map map) throws Exception;
}
