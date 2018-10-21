package cn.fintecher.system.mapper;


import cn.fintecher.system.model.GoodsCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsCategoryMapper {

    int deleteByPrimaryKey(String id);
    //新增商品类别
    int insert(GoodsCategory record);

    int insertSelective(GoodsCategory record);

    //获取商品类别
    GoodsCategory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GoodsCategory record);

    //修改商品类别
    int updateByPrimaryKey(GoodsCategory record);

    //根据父级id获取子集
    List<GoodsCategory> getGoodsCategoryByParentId(String parentId);

    //获取商品数据集合
    List<GoodsCategory> getList(Map<String, Object> map);

}