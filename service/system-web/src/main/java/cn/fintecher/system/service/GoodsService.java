package cn.fintecher.system.service;

import cn.fintecher.system.model.EntPromotion;
import cn.fintecher.system.model.Goods;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @商品管理@
 * Create on : 2018年06月12日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
public interface GoodsService {

    //添加商品
    @Transactional
    int addGoods(Goods goods) throws Exception;

    //修改商品
    int updateGoods(Goods goods)throws Exception;

    //删除商品
    int delGoods(Goods goods)throws Exception;

    //商品集合
    List<Goods> getList(Map<String, Object> map) throws Exception;

    //商品分配活动
    boolean assignActivity(Map map)throws Exception;

    //获取分配过的活动
    List<EntPromotion> getAssignActivity(String goodsCode)throws Exception;

    //上架、下架
    int updateStatus(Map map)throws Exception;

    //获取商品
    Goods getGoodsById(String id)throws Exception;

    //修改商品上下架
    int updateGoodsStatus(Goods goods)throws Exception;
}
