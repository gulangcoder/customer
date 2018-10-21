package cn.fintecher.system.service;


import cn.fintecher.system.model.EntCustomer;

import java.util.Map;

/**
 * Title :
 * Description : @企业菜单@
 * Create on : 2018年06月08日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:chengrui
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
public interface EntCustomerService {
     //客户管理：查询客户列表  关联额度、逾期数据
     Map selectEntCustomers(Map map);

     ///关联查询客户详情
     Map getCustomerDetail(String customerId);

     Map updateCustomer(EntCustomer record);

}