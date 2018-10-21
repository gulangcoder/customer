package cn.fintecher.app.mapper.product;


import cn.fintecher.app.model.product.EntProductDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EntProductDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntProductDetail record);

    int insertSelective(EntProductDetail record);

    EntProductDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntProductDetail record);

    int updateByPrimaryKey(EntProductDetail record);

    /***
     * 获取系列下面的产品信息
     * @param entProductDetail
     * @return List
     */
    List<EntProductDetail> getEntProductDetailList(EntProductDetail entProductDetail);
}