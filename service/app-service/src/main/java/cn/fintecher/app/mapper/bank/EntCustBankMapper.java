package cn.fintecher.app.mapper.bank;

import cn.fintecher.app.model.bank.EntCustBank;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface EntCustBankMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntCustBank record);

    int insertSelective(EntCustBank record);

    EntCustBank selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntCustBank record);

    int updateByPrimaryKey(EntCustBank record);

    List<EntCustBank> findByCustId(Map param);

    int updateBankStatus(String custId);
}