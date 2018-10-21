package cn.fintecher.system.service.impl;

import cn.fintecher.system.mapper.CompanyMapper;
import cn.fintecher.system.model.Company;
import cn.fintecher.system.service.CompanyService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
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
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 11:07
     * @Params: company
     *              企业实体
     * @Description: 获取企业列表
     * @return:
     */
    @Override
    public List<Company> getCompanyList(Map map) throws Exception{
        Company company = new Company();
        String companyName = String.valueOf(map.get("companyName"));
        if(StringUtil.isNotEmpty(companyName)){
            company.setCompanyName(companyName);
        }
        List<Company> list = companyMapper.getCompanyList(company);
        return list;
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 13:40
     * @Params: company
     *              企业实体
     * @Description: 添加企业
     * @return:
     */
    @Override
    public Map addCompany(Company company) throws Exception{
        Map map = new HashMap();
        String companyCode = CreateIDUtil.getId();
        Date createTime = new Date();
        company.setCompanyCode(companyCode);
        company.setCreateTime(createTime);
        int n = companyMapper.insertSelective(company);
        if(n>0){
            map.put("flag",true);
            map.put("msg","message.system.save.success");
        }else{
            map.put("flag",false);
            map.put("msg","message.system.save.fail");
        }
        return map;
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 13:45
     * @Params: company
     *              企业实体
     * @Description: 修改企业
     * @return:
     */
    @Override
    public Map updateCompany(Company company) throws Exception{
        Map map = new HashMap();
        company.setUpdateTime(new Date());
        int n = companyMapper.updateByPrimaryKeySelective(company);
        if(n>0){
            map.put("flag",true);
            map.put("msg","message.system.update.success");
        }else{
            map.put("flag",false);
            map.put("msg","message.system.update.fail");
        }
        return map;
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 15:20
     * @Params: companyCode
     *              企业code
     * @Description: 修改回显数据
     * @return: Company
     */
    @Override
    public Company getCompanyById(String companyCode) throws Exception{
        Company company = companyMapper.selectByPrimaryKey(companyCode);
        return company;
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 18:33
     * @Params:
     * @Description: 获取所有启用的企业
     * @return: ResultVO
     */
    @Override
    public List getAllCompanyList() throws Exception{
        List list = companyMapper.getAllCompanyList();
        return list;
    }
}
