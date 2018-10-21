package cn.fintecher.system.service.impl;

import cn.fintecher.system.mapper.GoodsCategoryMapper;
import cn.fintecher.system.model.GoodsCategory;
import cn.fintecher.system.service.GoodsCategoryService;
import cn.fintecher.util.CreateIDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
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
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;
    //新增商品类别
    @Override
    public  Map<String,Object> addGoodsCategory(GoodsCategory goodsCategory)throws Exception{
        Map<String,Object> paramMap=new HashMap<>();
        Map<String,Object> paramNameMap=new HashMap<>();
        Map<String,Object> resultMap=new HashMap<>();
        paramMap.put("categoryCode",goodsCategory.getCategoryCode());
        //paramMap.put("parentId",goodsCategory.getParentId());
        paramMap.put("companyCode",goodsCategory.getCompanyCode());
        List<GoodsCategory> goodsCategoryList=goodsCategoryMapper.getList(paramMap);
        if (goodsCategoryList.size()>0){
            resultMap.put("flag",false);
            resultMap.put("msg","message.category.code.existed");
            return  resultMap;
        }
        paramNameMap.put("cpategoryName",goodsCategory.getCategoryName());
        paramNameMap.put("companyCode",goodsCategory.getCompanyCode());
        //paramNameMap.put("parentId",goodsCategory.getParentId());
        List<GoodsCategory> goodsCategoryNameList=goodsCategoryMapper.getList(paramNameMap);
        if (goodsCategoryNameList.size()>0){
            resultMap.put("flag",false);
            resultMap.put("msg","message.category.name.existed");
            return  resultMap;
        }

        Date now = new Date();
        goodsCategory.setCreateTime(now);
        goodsCategory.setId(CreateIDUtil.getId());
        //goodsCategory.setStatus((short) 1);
        if (goodsCategoryMapper.insertSelective(goodsCategory)>0){
            resultMap.put("flag",true);
            resultMap.put("msg","message.system.save.success");
            return  resultMap;
        }else {
            resultMap.put("flag",false);
            resultMap.put("msg","message.system.save.fail");
            return  resultMap;
        }
    }

    //更新商品类别
    @Override
    public Map<String,Object>  updateGoodsCategory(GoodsCategory goodsCategory)throws Exception{
        Map<String,Object> paramMap=new HashMap<>();
        Map<String,Object> paramNameMap=new HashMap<>();
        Map<String,Object> resultMap=new HashMap<>();
        paramMap.put("categoryCode",goodsCategory.getCategoryCode());
        //paramMap.put("parentId",goodsCategory.getParentId());
        paramMap.put("companyCode",goodsCategory.getCompanyCode());
        List<GoodsCategory> goodsCategoryList=goodsCategoryMapper.getList(paramMap);
        if (goodsCategoryList.size()>2){
            resultMap.put("flag",false);
            resultMap.put("msg","message.category.code.existed");
            return  resultMap;
        }
        if (goodsCategoryList.size()==1){
            if (!goodsCategory.getId().equals(goodsCategoryList.get(0).getId())){
                resultMap.put("flag",false);
                resultMap.put("msg","message.category.code.existed");
                return  resultMap;
            }
        }
        paramNameMap.put("cpategoryName",goodsCategory.getCategoryName());
        paramNameMap.put("companyCode",goodsCategory.getCompanyCode());
        //paramNameMap.put("parentId",goodsCategory.getParentId());
        List<GoodsCategory> goodsCategoryNameList=goodsCategoryMapper.getList(paramNameMap);
        if (goodsCategoryNameList.size()>2){
            resultMap.put("flag",false);
            resultMap.put("msg","message.category.name.existed");
            return  resultMap;
        }
        if (goodsCategoryNameList.size()==1){
            if (!goodsCategory.getId().equals(goodsCategoryNameList.get(0).getId())){
                resultMap.put("flag",false);
                resultMap.put("msg","message.category.name.existed");
                return  resultMap;
            }
        }
        Date now = new Date();
        goodsCategory.setUpdateTime(now);
        if(goodsCategoryMapper.updateByPrimaryKeySelective(goodsCategory)>0){
            resultMap.put("flag",true);
            resultMap.put("msg","message.system.update.success");
            return  resultMap;
        }else {
            resultMap.put("flag",false);
            resultMap.put("msg","message.system.update.fail");
            return  resultMap;
        }
    }

    //删除商品类别
    @Override
    public Map delGoodsCategory(GoodsCategory goodsCategory)throws Exception{
        Map map=new HashMap<>();
        if (goodsCategoryMapper.getGoodsCategoryByParentId(goodsCategory.getId()).size()>0){
            map.put("flag",false);
            map.put("msg","message.category.existed.children");
            return  map;
        }
        Date now = new Date();
        goodsCategory.setUpdateTime(now);
        goodsCategory.setStatus((short)2);
        if (goodsCategoryMapper.updateByPrimaryKeySelective(goodsCategory)==1){
            map.put("flag",true);
            map.put("msg","message.system.delete.success");
        }else {
            map.put("flag",false);
            map.put("msg","message.system.delete.fail");
        }
        return  map;
    }

    //商品类别集合
    @Override
    public List<GoodsCategory> getList(Map<String,Object> map) throws Exception{
        return goodsCategoryMapper.getList(map);
    }

    //获取商品类别
    @Override
    public GoodsCategory getGoodsCategory(String id) throws Exception{
        return goodsCategoryMapper.selectByPrimaryKey(id);
    }

    //获取商品类型联动
    @Override
    public List<GoodsCategory> getGoodsCategoryLstByParentId(Map<String,Object> map) throws Exception{
        return goodsCategoryMapper.getList(map);
    }
}
