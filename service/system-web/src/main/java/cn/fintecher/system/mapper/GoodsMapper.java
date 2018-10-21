package cn.fintecher.system.mapper;


import cn.fintecher.system.model.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    //获取商品集合
    List<Goods> getList(Map<String, Object> map);

    //添加商品图片
    int insertImg(Map<String, Object> img);

    //删除商品图片
    int deleteImg(String goodsCode);

    //获取商品图片
    List<Map> getImgByGoodsId(String goodsCode);

    //上架、下架
    int updateStatus(Map map);
}