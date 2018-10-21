package cn.fintecher.system.mapper;

import cn.fintecher.system.model.EntProductDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntProductDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntProductDetail record);

    int insertSelective(EntProductDetail record);

    EntProductDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntProductDetail record);

    int updateByPrimaryKey(EntProductDetail record);

    List<EntProductDetail> seletctProductDetail(Map map);

    EntProductDetail checkProductSequence(Map map);

    EntProductDetail checkProductName(Map map);

    int updatePriductDteailStatus(Map map);

    List<EntProductDetail> getProductNameList(Map map);
}