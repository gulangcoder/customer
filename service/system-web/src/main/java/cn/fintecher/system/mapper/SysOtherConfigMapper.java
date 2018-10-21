package cn.fintecher.system.mapper;


import cn.fintecher.system.model.SysOtherConfig;

import java.util.List;
import java.util.Map;

public interface SysOtherConfigMapper {
    int deleteByPrimaryKey(String id);


    int insertSelective(SysOtherConfig record);

    SysOtherConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysOtherConfig record);


    List<SysOtherConfig> findList(Map map);

    List<SysOtherConfig> getConfListByCompanyCode(String companyCode);
}