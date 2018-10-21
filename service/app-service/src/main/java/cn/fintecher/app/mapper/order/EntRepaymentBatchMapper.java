package cn.fintecher.app.mapper.order;


import cn.fintecher.app.model.order.EntRepaymentBatch;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EntRepaymentBatchMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntRepaymentBatch record);

    int insertSelective(EntRepaymentBatch record);

    EntRepaymentBatch selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntRepaymentBatch record);

    int updateByPrimaryKey(EntRepaymentBatch record);
}