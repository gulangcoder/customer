package cn.fintecher.app.mapper.customer;

import cn.fintecher.app.model.customer.EntCustProductApply;
import cn.fintecher.app.model.customer.EntProductVideo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntCustProductApplyMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntCustProductApply record);

    int insertSelective(EntCustProductApply record);

    EntCustProductApply selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntCustProductApply record);

    int updateByPrimaryKey(EntCustProductApply record);

    EntCustProductApply selectProductApplyByMap(Map map);

    int updateStatusByMap(Map map);

    List<EntProductVideo> selectProductVideoByProdictId(Map map);

    EntCustProductApply selectProductIdByMap(Map map);
}