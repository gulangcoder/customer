package cn.fintecher.app.mapper.product;


import cn.fintecher.app.model.product.EntProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EntProductMapper {

    int deleteByPrimaryKey(String id);

    int insert(EntProduct record);

    int insertSelective(EntProduct record);

    EntProduct selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntProduct record);

    int updateByPrimaryKey(EntProduct record);

    /***
     * 获取产品集合
     * @param record
     * @return
     */
    List<EntProduct> getEntProductList(EntProduct record);

    /***
     * 获取产品详情
     * @param productId
     * @return
     */
    EntProduct getEntProductById(String productId);
}