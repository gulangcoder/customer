package cn.fintecher.app.service.product.impl;

import cn.fintecher.app.mapper.product.EntProductCreditMapper;
import cn.fintecher.app.mapper.product.EntProductMapper;
import cn.fintecher.app.model.order.EntCustQuota;
import cn.fintecher.app.model.product.EntProduct;
import cn.fintecher.app.model.product.EntProductCredit;
import cn.fintecher.app.service.order.QuotaService;
import cn.fintecher.app.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Title :* 产品系列管理接口实现*
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
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private EntProductMapper entProductMapper;

    @Autowired
    private EntProductCreditMapper entProductCreditMapper;

    @Autowired
    private QuotaService quotaService;
    /**
     * 产品系列验证
     * @param entProduct
     * @return boolean
     */
    @Override
    public boolean validation(EntProduct entProduct){
        if (entProduct.getId()==null||"".equals(entProduct.getId().trim())){
            return false;
        }
        entProduct=entProductMapper.selectByPrimaryKey(entProduct.getId());
        if(entProduct==null){
            return false;
        }
        if(entProduct.getStatus()==(short)1){
            return true;
        }else {
            return  false;
        }
    }

    /***
     * 获取产品信息
     * @param entProduct
     * @return
     */
    @Override
    public List<EntProduct> getProductList(EntProduct entProduct){
        List<EntProduct> productList=entProductMapper.getEntProductList(entProduct);
        return productList;
    }

    /***
     * 获取产品详情信息
     * @param productId
     * @return List
     */
    @Override
    public EntProduct getProductById(String productId){
        return entProductMapper.getEntProductById(productId);
    }

    /***
     * 获取产品信息
     * @param custId
     * @return
     */
    @Override
    public List<EntProductCredit> getProductCredit(EntProductCredit entProductCredit,String custId) throws Exception{
        EntCustQuota quota=new  EntCustQuota();
        quota.setCustId(custId);
        quota=quotaService.getQuota(quota);
        entProductCredit.setProductDetailId(quota.getProductDetailId());
        return entProductCreditMapper.getEntProductCreditList(entProductCredit);
    }
}
