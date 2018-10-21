package cn.fintecher.system.mapper;

import cn.fintecher.system.model.EntProductCompanyRate;

import java.util.List;
import java.util.Map;

public interface EntProductCompanyRateMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntProductCompanyRate record);

    int insertSelective(EntProductCompanyRate record);

    EntProductCompanyRate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntProductCompanyRate record);

    int updateByPrimaryKey(EntProductCompanyRate record);

    int updateProductCompanyRate(Map productCompanyRate);

    List<EntProductCompanyRate> selectCompanyRateByMap(Map map);
}