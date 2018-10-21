package cn.fintecher.app.service.product;

import cn.fintecher.app.model.product.EntProduct;
import cn.fintecher.app.model.product.EntProductCredit;

import java.util.List;

/**
 * Title :* 产品系列管理接口*
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 * @Description: * *
 * Create on : 2018/9/17 11:00
 * Copyright (C) zw.FinTec
 */
public interface ProductService {

    /**
     * 产品系列验证
     * @param entProduct
     * @return boolean
     */
    boolean validation(EntProduct entProduct);

    /***
     * 获取产品信息
     * @param entProduct
     * @return List
     */
    List<EntProduct> getProductList(EntProduct entProduct);

    /***
     * 获取产品详情信息
     * @param productId
     * @return List
     */
    EntProduct getProductById(String productId);

    /***
     * 获取产品信率
     * @param custId
     * @return
     */
    List<EntProductCredit> getProductCredit(EntProductCredit entProductCredit,String custId) throws Exception;
}
