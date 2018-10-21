package cn.fintecher.system.mapper;

import cn.fintecher.system.model.EntProduct;
import cn.fintecher.system.model.ProductCust;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntProductMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntProduct record);

    int insertSelective(EntProduct record);

    EntProduct selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntProduct record);

    int updateByPrimaryKey(EntProduct record);

    List<EntProduct> getProductList(Map map);

    EntProduct checkSeriesSequence(Map map);

    EntProduct checkSeries(Map map);

    List<EntProduct> getAllList(Map map);

    int updateProductStatus(Map map);

    Map getProductInfo(Map map);

    List<ProductCust> getProductCustList(Map map);

    String getProductRate(Map map);
}