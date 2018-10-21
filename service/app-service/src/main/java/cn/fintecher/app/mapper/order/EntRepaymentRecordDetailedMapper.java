package cn.fintecher.app.mapper.order;


import cn.fintecher.app.model.order.EntRepaymentRecordDetailed;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntRepaymentRecordDetailedMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntRepaymentRecordDetailed record);

    int insertSelective(EntRepaymentRecordDetailed record);

    EntRepaymentRecordDetailed selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntRepaymentRecordDetailed record);

    int updateByPrimaryKey(EntRepaymentRecordDetailed record);

    /***
     * 批量新增
     * @param record
     * @return
     */
    int insertBatch(List<EntRepaymentRecordDetailed> record);

    /**
     * 根据还款流水id获取对应的还款计划列表
     * @param repaymentRecordDetailed
     * @return
     */
    List<Map> getPaidList(EntRepaymentRecordDetailed repaymentRecordDetailed);

    List<Map> getRepaymentDetail(String repaymentRecordId);
}