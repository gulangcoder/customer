package cn.fintecher.system.service.impl;

import cn.fintecher.system.mapper.EntProductDetailMapper;
import cn.fintecher.system.mapper.EntProductMapper;
import cn.fintecher.system.mapper.EntProductVideoMapper;
import cn.fintecher.system.model.EntProduct;
import cn.fintecher.system.model.EntProductDetail;
import cn.fintecher.system.model.EntProductVideo;
import cn.fintecher.system.model.ProductCust;
import cn.fintecher.system.service.DictDetailService;
import cn.fintecher.system.service.ProductService;
import cn.fintecher.util.CreateIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    private EntProductMapper entProductMapper;

    @Autowired
    private EntProductDetailMapper entProductDetailMapper;

    @Autowired
    private EntProductVideoMapper entProductVideoMapper;

    @Autowired
    private DictDetailService dictDetailService;

    @Override
    public List<EntProduct> getProductList(Map map) throws Exception{
        List<EntProduct> productList = entProductMapper.getProductList(map);
        return productList;
    }

    @Override
    public EntProduct checkSeriesSequence(Map map) throws Exception{
        EntProduct product = entProductMapper.checkSeriesSequence(map);
        return product;
    }

    @Override
    public EntProduct checkSeries(Map map) throws Exception{
        EntProduct product = entProductMapper.checkSeries(map);
        return product;
    }

    @Transactional
    @Override
    public int addProduct(EntProduct product) throws Exception{
        int row = entProductMapper.insertSelective(product);
        if (row == 1) {
            //选择的影像资料
            int num = addVideo(product);
            if (num == 0){
                return num;
            }
        }
        return row;
    }

    @Transactional
    @Override
    public int updateProduct(EntProduct product) throws Exception{
        int row = entProductMapper.updateByPrimaryKeySelective(product);
        //先将之前的关联影像资料逻辑删除 再重新添加
        EntProductVideo productVideo = new EntProductVideo();
        productVideo.setProductId(product.getId());
        productVideo.setDeletedFlag("0");
        productVideo.setUpdateUser(product.getUpdateUser());
        productVideo.setUpdateTime(new Date());
        entProductVideoMapper.updateProductVideo(productVideo);
        //重新插入影像资料
        int num = addVideo(product);
        if (num == 0){
            return num;
        }
        return row;
    }

    @Override
    public List<EntProductDetail> seletctProductDetail(Map map) throws Exception{
        return entProductDetailMapper.seletctProductDetail(map);
    }

    @Override
    public List<EntProduct> getAllList(Map map) throws Exception{
        return entProductMapper.getAllList(map);
    }

    @Override
    public EntProduct getProductById(String id) throws Exception{
        EntProduct product = entProductMapper.selectByPrimaryKey(id);
        Map map = new HashMap();
        map.put("productDetailId",id);
        List<EntProductVideo> videoList = entProductVideoMapper.selectVideoByMap(map);
        for (EntProductVideo productVideo:videoList) {
            String vidoTypeHH = dictDetailService.getDictNameByDictItemAndDetailCode("videoData",productVideo.getVideoType());
            productVideo.setVideoTypeHH(vidoTypeHH);
        }
        product.setVideoList(videoList);
        return product;
    }

    @Override
    public int updateProductStatus(Map map) throws Exception{
        return entProductMapper.updateProductStatus(map);
    }

    @Override
    public Map getProductInfo(Map map) throws Exception{
        return entProductMapper.getProductInfo(map);
    }

    @Override
    public List<ProductCust> getProductCustList(Map map) {
        List<ProductCust> list = entProductMapper.getProductCustList(map);
        for(int i=0;i<list.size();i++){
            Map pMap = new HashMap();
            String productId = list.get(i).getProductId();
            pMap.put("productId",productId);
            String rate = entProductMapper.getProductRate(pMap);
            if("".equals(rate)||rate==null){

            }else {
                list.get(i).setRate(new BigDecimal(rate));
            }
        }
        return list;
    }

    public int addVideo(EntProduct product) throws Exception{
        //选择的影像资料
        if (!"".equals(product.getShadowData())) {
            String[] vedioList = product.getShadowData().split(",");
            int vdeioNum = 0;
            for (int i = 0; i < vedioList.length; i++) {
                EntProductVideo entProductVideo = new EntProductVideo();
                entProductVideo.setId(CreateIDUtil.getId());
                entProductVideo.setProductId(product.getId());
                entProductVideo.setVideoType(vedioList[i]);
                entProductVideo.setCreateUser(product.getCreateUser()==null?null:product.getCreateUser());
                entProductVideo.setCreateTime(new Date());
                entProductVideo.setUpdateUser(product.getUpdateUser());
                entProductVideo.setUpdateTime(new Date());
                entProductVideo.setDeletedFlag("1");
                vdeioNum = vdeioNum + entProductVideoMapper.insertSelective(entProductVideo);
            }
            if (vdeioNum != vedioList.length) {
                return 0;
            }
        }
        return  1;
    }
}
