package cn.fintecher.app.service.customer;

import cn.fintecher.app.model.customer.EntCustomer;
import cn.fintecher.app.model.customer.EntCustomerInfo;
import cn.fintecher.app.model.customer.EntCustomerRelationship;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月19日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
public interface CustomerInfoService {

    Map updateCustInfo(EntCustomerInfo customerInfo)throws Exception;
    Map updateCust(EntCustomer customer)throws Exception;

    EntCustomerInfo getInfoByCustId(String custId)throws Exception;

    /**
     * 查询用户详情[后管]
     * @param custId 客户详情id
     * @return
     */
    Map getCustomerDetail(String custId);

    /**
     * @description: 更新联系人信息
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/20
     */
    Map updateCustomerRelationship(EntCustomerRelationship customerRelationship) throws Exception;

    /**
     * @description: 查询联系人信息
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/20
     */
    EntCustomerRelationship selectCustomerRelationship() throws Exception;

    /**
     * @description: 更新客户产品申请关系
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/20
     */
    Map updateCustProduct(Map map)throws Exception;

    /**
     * 获取客户列表 后管
     * @param map
     * @return
     * @throws Exception
     */
    List<Map> selectEntCustomerList(Map map) throws Exception;

    /**
     * 授信信息
     * @param custId
     * @return
     */
    Map getCreditDetail(String custId);

    EntCustomerInfo getCustomerInfoById(String userId)throws Exception;
    /**
     * @Description 更新客户的app别名
     * @Param 
     * @return 
     * @Author coder_bao
     * @Date 2018/10/10 13:24
     */
    void updateCustomerAppAliasById(Map map)throws Exception;
}
