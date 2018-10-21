package cn.fintecher.system.mapper;

import cn.fintecher.system.model.EntProductDetailCompany;

import java.util.List;
import java.util.Map;

public interface EntProductDetailCompanyMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntProductDetailCompany record);

    int insertSelective(EntProductDetailCompany record);

    EntProductDetailCompany selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntProductDetailCompany record);

    int updateByPrimaryKey(EntProductDetailCompany record);

    List<EntProductDetailCompany> selectAssCompanyByMap(Map map);

    int updateProductDetailCompany(EntProductDetailCompany entProductDetailCompany);
}