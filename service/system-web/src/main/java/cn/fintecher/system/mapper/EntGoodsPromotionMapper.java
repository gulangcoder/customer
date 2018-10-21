package cn.fintecher.system.mapper;

import cn.fintecher.system.model.EntGoodsPromotion;
import cn.fintecher.system.model.EntPromotion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EntGoodsPromotionMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntGoodsPromotion record);

    int insertSelective(EntGoodsPromotion record);

    EntGoodsPromotion selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntGoodsPromotion record);

    int updateByPrimaryKey(EntGoodsPromotion record);

    //根据商品删除之前的活动
    int deleteByGoodsCode(@Param("goodsCode") String goodsCode);

    //获取商品选择过的活动
    List<EntPromotion> getListByGoodsCode(@Param("goodsCode") String goodsCode);
}