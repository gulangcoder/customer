package cn.fintecher.system.service;



import cn.fintecher.system.model.GoodsCategory;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @商品类别@
 * Create on : 2018年06月12日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
public interface GoodsCategoryService {

    //新增商品类别
    Map<String,Object> addGoodsCategory(GoodsCategory goodsCategory) throws Exception;

    //修改商品类别
    Map<String,Object>  updateGoodsCategory(GoodsCategory goodsCategory) throws Exception;

    //删除商品类别
    Map delGoodsCategory(GoodsCategory goodsCategory) throws Exception;

    //商品类别集合
    List<GoodsCategory> getList(Map<String, Object> map) throws Exception;

    //获取商品类别
    GoodsCategory getGoodsCategory(String id) throws Exception;

    //获取商品类型联动
    List<GoodsCategory> getGoodsCategoryLstByParentId(Map<String, Object> map) throws Exception;
}
