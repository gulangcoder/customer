package cn.fintecher.system.service.impl;

import cn.fintecher.system.mapper.EntProductCreditMapper;
import cn.fintecher.system.mapper.EntProductDetailCompanyMapper;
import cn.fintecher.system.mapper.EntProductDetailMapper;
import cn.fintecher.system.mapper.EntProductVideoMapper;
import cn.fintecher.system.model.EntProductCredit;
import cn.fintecher.system.model.EntProductDetail;
import cn.fintecher.system.model.EntProductDetailCompany;
import cn.fintecher.system.model.EntProductVideo;
import cn.fintecher.system.service.ProductDetailService;
import cn.fintecher.util.CreateIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/5
 * @Description:
 */
@Service
public class ProductDetailServiceImpl implements ProductDetailService{

    @Autowired
    private EntProductDetailMapper entProductDetailMapper;

    @Autowired
    private EntProductDetailCompanyMapper entProductDetailCompanyMapper;

    @Autowired
    private EntProductCreditMapper entProductCreditMapper;

    @Override
    public List<EntProductDetail> getProductDeatilList(Map map) throws Exception{
        List<EntProductDetail> productDetailList = entProductDetailMapper.seletctProductDetail(map);
        return productDetailList;
    }

    @Override
    public EntProductDetail checkProductSequence(Map map) throws Exception{
        return entProductDetailMapper.checkProductSequence(map);
    }

    @Override
    public EntProductDetail checkProductName(Map map) throws Exception{
        return entProductDetailMapper.checkProductName(map);
    }

    @Transactional
    @Override
    public int addProductDetail(EntProductDetail productDetail)  throws Exception{
        int row = entProductDetailMapper.insertSelective(productDetail);
        if (row == 1) {
            //选择的关联公司 目前是获取组织架构中的类型为公司
            int assNumber = addAssociatedCompany(productDetail);
            if (assNumber == 0){
                return assNumber;
            }
        }
        return row;
    }

    @Transactional
    @Override
    public int updatePriductDteail(EntProductDetail productDetail)  throws Exception{
        int row = entProductDetailMapper.updateByPrimaryKeySelective(productDetail);
        if (row == 1) {
            //先将之前的关联公司逻辑删除 再重新添加
            EntProductDetailCompany entProductDetailCompany = new EntProductDetailCompany();
            entProductDetailCompany.setProductId(productDetail.getId());
            entProductDetailCompany.setDeletedFlag((short)0);
            entProductDetailCompany.setUpdateUser(productDetail.getUpdateUser());
            entProductDetailCompany.setUpdateTime(new Date());
            entProductDetailCompanyMapper.updateProductDetailCompany(entProductDetailCompany);
            //重新插入关联公司
            int assNumber = addAssociatedCompany(productDetail);
            if (assNumber == 0){
                return assNumber;
            }
        }
        return row;
    }

    @Override
    public EntProductDetail getProductDeatilById(String productDetailId) throws Exception{
        EntProductDetail productDetail = entProductDetailMapper.selectByPrimaryKey(productDetailId);
        Map map = new HashMap();
        map.put("productDetailId",productDetailId);
        List<EntProductDetailCompany> associatedCompanyList = entProductDetailCompanyMapper.selectAssCompanyByMap(map);
        productDetail.setAssociatedCompanyList(associatedCompanyList);
        return productDetail;
    }

    @Override
    public List<EntProductCredit> selectCreditListByMap(Map statusMap) throws Exception{
        return entProductCreditMapper.selectCreditListByMap(statusMap);
    }

    @Override
    public int updatePriductDteailStatus(Map map) throws Exception{
        return entProductDetailMapper.updatePriductDteailStatus(map);
    }

    @Override
    public List<EntProductDetail> getProductNameList(Map map) throws Exception{
        List<EntProductDetail> productDetailList = entProductDetailMapper.getProductNameList(map);
        return productDetailList;
    }


    public int addAssociatedCompany(EntProductDetail productDetail){
        //选择的关联公司
        if (!"".equals(productDetail.getAssociatedCompany())) {
            String[] associatedCompanyList = productDetail.getAssociatedCompany().split(",");
            int assNum = 0;
            for (int i = 0; i < associatedCompanyList.length; i++) {
                EntProductDetailCompany entProductDetailCompany = new EntProductDetailCompany();
                entProductDetailCompany.setId(CreateIDUtil.getId());
                entProductDetailCompany.setProductId(productDetail.getId());
                entProductDetailCompany.setCompanyId(associatedCompanyList[i]);
                entProductDetailCompany.setCompanyCode(productDetail.getCompanyCode());
                entProductDetailCompany.setCreateUser(productDetail.getCreateUser()==null?null:productDetail.getCreateUser());
                entProductDetailCompany.setCreateTime(new Date());
                entProductDetailCompany.setUpdateUser(productDetail.getUpdateUser());
                entProductDetailCompany.setUpdateTime(new Date());
                entProductDetailCompany.setDeletedFlag((short)1);
                entProductDetailCompany.setType((short)0);
                assNum = assNum + entProductDetailCompanyMapper.insertSelective(entProductDetailCompany);
            }
            if (assNum != associatedCompanyList.length) {
                return 0;
            }
        }
        return  1;
    }
}
