package cn.fintecher.system.service.impl;

import cn.fintecher.system.mapper.EntGoodsPromotionMapper;
import cn.fintecher.system.mapper.GoodsMapper;
import cn.fintecher.system.model.EntGoodsPromotion;
import cn.fintecher.system.model.EntPromotion;
import cn.fintecher.system.model.Goods;
import cn.fintecher.system.service.GoodsService;
import cn.fintecher.util.CreateIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
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
@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;
    @Autowired
    private EntGoodsPromotionMapper entGoodsPromotionMapper;
    //添加商品
    @Override
    public int addGoods (Goods goods) throws Exception{
        Date now = new Date();
        goods.setCreateTime(now);
        goods.setId(CreateIDUtil.getId());
        if (goods.getImgPath()!=null&&!"".equals(goods.getImgPath())){
            String[] imgList=goods.getImgPath().split("\\|");
            for(int i=0;i<imgList.length;i++){
                if(i==0){
                    goods.setImageUrl(imgList[i]);
                }else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", CreateIDUtil.getId());
                    map.put("goodsCode", goods.getId());
                    map.put("imageUrl", imgList[i]);
                    goodsMapper.insertImg(map);
                }
            }
        }
        return goodsMapper.insertSelective(goods);
    }

    //修改商品
    @Transactional
    @Override
    public int updateGoods (Goods goods)throws Exception{
        Date now = new Date();
        goods.setUpdateTime(now);
        goodsMapper.deleteImg(goods.getId());
        if (goods.getImgPath()!=null&&!"".equals(goods.getImgPath())){
            String[] imgList=goods.getImgPath().split("\\|");
            for(int i=0;i<imgList.length;i++){
                if(i==0){
                    goods.setImageUrl(imgList[i]);
                }else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", CreateIDUtil.getId());
                    map.put("goodsCode", goods.getId());
                    map.put("imageUrl", imgList[i]);
                    goodsMapper.insertImg(map);
                }
            }
        }
        return  goodsMapper.updateByPrimaryKeySelective(goods);
    }

    //删除商品
    @Override
    public int delGoods (Goods goods)throws Exception{
        Date now = new Date();
        goods.setUpdateTime(now);
        goods.setStatus((short)2);
        return goodsMapper.updateByPrimaryKeySelective(goods);
    }

    //商品类别集合
    @Override
    public List<Goods> getList(Map<String,Object> map) throws Exception{
        return goodsMapper.getList(map);
    }

    @Override
    public boolean assignActivity(Map map) throws Exception{
        //选择的活动
        List<String> activeIds = (List<String>) map.get("activeIds");
        //商品id
        String goodsId = String.valueOf(map.get("goodsId"));
        //先删除之前的活动
        entGoodsPromotionMapper.deleteByGoodsCode(goodsId);
        //再添加选择的活动
        if(!ObjectUtils.isEmpty(activeIds)){
            for(int i=0;i<activeIds.size();i++){
                EntGoodsPromotion entGoodsPromotion = new EntGoodsPromotion();
                entGoodsPromotion.setId(CreateIDUtil.getId());
                entGoodsPromotion.setGoodsCode(goodsId);
                entGoodsPromotion.setPromotionCode(activeIds.get(i));
                entGoodsPromotionMapper.insertSelective(entGoodsPromotion);
            }
        }
        return true;
    }

    @Override
    public List<EntPromotion> getAssignActivity(String goodsCode) throws Exception{
        return entGoodsPromotionMapper.getListByGoodsCode(goodsCode);
    }

    @Override
    public int updateStatus(Map map)throws Exception {
        return goodsMapper.updateStatus(map);
    }

    //获取商品
    public Goods getGoodsById (String id)throws Exception{
        Goods goods=goodsMapper.selectByPrimaryKey(id);
        goods.setImageList(goodsMapper.getImgByGoodsId(id));
        return goods;
    }

    //修改商品上下架
    @Override
    public int updateGoodsStatus (Goods goods)throws Exception{
        Date now = new Date();
        goods.setUpdateTime(now);
        return  goodsMapper.updateByPrimaryKeySelective(goods);
    }
}
