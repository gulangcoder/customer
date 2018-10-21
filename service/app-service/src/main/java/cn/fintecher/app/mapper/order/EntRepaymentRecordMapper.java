package cn.fintecher.app.mapper.order;


import cn.fintecher.app.model.order.EntRepaymentRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntRepaymentRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntRepaymentRecord record);

    int insertSelective(EntRepaymentRecord record);

    EntRepaymentRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntRepaymentRecord record);

    int updateByPrimaryKey(EntRepaymentRecord record);

    List<Map> getRepaymentRecordList(Map map);

    List<EntRepaymentRecord> getRepaymentRecordListByUser(Map<String,Object>map);
}