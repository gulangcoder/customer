package cn.fintecher.system.mapper;


import cn.fintecher.system.model.SysEntOtherConfig;

import java.util.List;
import java.util.Map;

public interface SysEntOtherConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysEntOtherConfig record);

    int insertSelective(SysEntOtherConfig record);

    SysEntOtherConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysEntOtherConfig record);

    int updateByPrimaryKey(SysEntOtherConfig record);


    List<SysEntOtherConfig> findListByCompanyCode(String companyCode);

    int deleteByCompanyCode(String companyCode);

    int batchSave(List<SysEntOtherConfig> list);

    List<Map>findNotInSysParaList(String companyCode);
}