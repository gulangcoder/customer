package cn.fintecher.system.mapper;

import cn.fintecher.system.model.EntContractTemplet;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntContractTempletMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntContractTemplet record);

    int insertSelective(EntContractTemplet record);

    EntContractTemplet selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntContractTemplet record);


    int updateByPrimaryKey(EntContractTemplet record);


    /**
     * 查询数据列表
     * @param map
     * @return
     */
    List<Map> findList(Map map);

    List<EntContractTemplet> getProtocol(Map parm);
}