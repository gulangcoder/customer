package cn.fintecher.system.service;


import cn.fintecher.system.model.Company;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description :
 * Create on : 2018年05月24日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username: wangtao
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
public interface CompanyService {
    /**
     * @Author: wangtao
     * @Date: 2018/05/24 11:07
     * @Params: company
     *              企业实体
     * @Description: 获取企业列表
     * @return:
     */
    List<Company> getCompanyList(Map map) throws Exception;

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 13:40
     * @Params: company
     *              企业实体
     * @Description: 添加企业
     * @return:
     */
    Map addCompany(Company company) throws Exception;

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 13:45
     * @Params: company
     *              企业实体
     * @Description: 修改企业
     * @return:
     */
    Map updateCompany(Company company) throws Exception;

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 15:20
     * @Params: companyCode
     *              企业code
     * @Description: 修改回显数据
     * @return: Company
     */
    Company getCompanyById(String companyCode) throws Exception;

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 18:33
     * @Params:
     * @Description: 获取所有启用的企业
     * @return: ResultVO
     */
    List getAllCompanyList() throws Exception;
}
