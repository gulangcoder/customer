package cn.fintecher.system.service;

import cn.fintecher.system.model.EntProduct;
import cn.fintecher.system.model.EntProductDetail;
import cn.fintecher.system.model.ProductCust;

import java.util.List;
import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/5
 * @Description:
 */
public interface ProductService {

    /**
     * @description: 获取产品系列列表
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    List<EntProduct> getProductList(Map map) throws Exception;

    /**
     * @description: 校验产品系列序列是否重复
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    EntProduct checkSeriesSequence(Map map) throws Exception;

    /**
     * @description: 校验产品系列是否重复
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    EntProduct checkSeries(Map map) throws Exception;

    /**
     * @description: 添加产品
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    int addProduct(EntProduct product) throws Exception;

    /**
     * @description: 修改产品
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    int updateProduct(EntProduct product) throws Exception;

    /**
     * @description: 根据参数查询产品详情集合
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    List<EntProductDetail> seletctProductDetail(Map map) throws Exception;

    /**
     * @description: 条件查询列表
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/6
     */
    List<EntProduct> getAllList(Map map) throws Exception;

    /**
     * @description: 获取产品明细
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/11
     */
    EntProduct getProductById(String id) throws Exception;

    /**
     * @description: 修改产品状态
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/12
     */
    int updateProductStatus(Map map) throws Exception;

    /**
     * @description: 未授信首页查询产品最高额度和最低利率
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/12
     */
    Map getProductInfo(Map map) throws Exception;

    /**
     * @description: 查询所有产品系列与客户关系
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/12
     */
    List<ProductCust> getProductCustList(Map map);
}
