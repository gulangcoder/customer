package cn.fintecher.system.mapper;

import cn.fintecher.system.model.EntProductCredit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntProductCreditMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntProductCredit record);

    int insertSelective(EntProductCredit record);

    EntProductCredit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntProductCredit record);

    int updateByPrimaryKey(EntProductCredit record);

    List<EntProductCredit> selectCreditListByMap(Map map);

    int updatePriductCreditStatus(Map map);

    List<Map> getPeriodsList(Map map);

    EntProductCredit getProductCreditByMap(Map map);

    List<Map> getProductNameList(Map map);
}