package cn.fintecher.app.service.customer.impl;

import cn.fintecher.app.client.AppFeginUtil;
import cn.fintecher.app.client.ManagerFeignUtil;
import cn.fintecher.app.mapper.customer.EntCustFileMapper;
import cn.fintecher.app.mapper.customer.EntCustProductApplyMapper;
import cn.fintecher.app.model.customer.EntCustFile;
import cn.fintecher.app.model.customer.EntCustProductApply;
import cn.fintecher.app.model.customer.EntProduct;
import cn.fintecher.app.model.customer.EntProductVideo;
import cn.fintecher.app.service.customer.CustFileService;
import cn.fintecher.app.service.sys.DictService;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月19日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@Service
public class CustFileServiceImpl implements CustFileService {

    @Autowired
    private EntCustFileMapper custFileMapper;

    @Autowired
    private EntCustProductApplyMapper custProductApplyMapper;

    @Autowired
    private DictService dictService;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 查询用户文件
     * @param custId
     * @param fileType
     * @return
     */
    @Override
    public List<EntCustFile> getCustFile(String custId, String fileType, String productId) throws Exception{
        String[] fileList = fileType.split(",");
        Map param = new HashMap();
        param.put("custId",custId);
        param.put("list", Arrays.asList(fileList));
        param.put("productId", productId);
        //param.put("fileType",fileType);
        param.put("companyCode", UserContextUtil.getUserInfo().getCompanyCode());
        return custFileMapper.findCustFile(param);
    }

    /**
     * 更新--插入用户文件
     * @param entCustFile
     * @return
     */
    @Transactional
    @Override
    public Map updateCustFile(EntCustFile entCustFile)  throws Exception{
        Map respMap = new HashMap();
        if(StringUtil.isEmpty(entCustFile.getCustId())){
            respMap.put("flag",false);
            respMap.put("msg","message.system.request.param.exception");
            return respMap;
        }
        UserInfo userInfo = UserContextUtil.getUserInfo();
        Map param = new HashMap();
        String[] fileList = entCustFile.getFileType().split(",");
        param.put("list",Arrays.asList(fileList));
        param.put("custId",userInfo.getUserId());
        param.put("companyCode", userInfo.getCompanyCode());
        List<EntCustFile> file = custFileMapper.findCustFile(param);
        int row =0;

        String[] fileUrlList = entCustFile.getFileUrl().split(",");
        List<EntCustFile> list = new ArrayList<>();
        if(fileList[0]!=null && !"".equals(fileList[0])){
            if("idcardFront".equals(fileList[0]) || "idcardBack".equals(fileList[0])){//身份证；1对1（fileType--fileUrl）
                if(fileList.length >0){
                    for (int i=0;i<fileList.length;i++) {
                        if(fileList[i] == null || fileList[i].isEmpty()) {
                            continue;
                        }
                        EntCustFile custFile = new EntCustFile();
                        custFile.setId(CreateIDUtil.getId());
                        custFile.setStatus((short)1);
                        custFile.setCompanyCode(UserContextUtil.getUserInfo().getCompanyCode());
                        custFile.setCustId(entCustFile.getCustId());
                        custFile.setFileType(fileList[i]);
                        custFile.setFileUrl(fileUrlList[i]);
                        //custFile.setProductId(entCustFile.getProductId());
                        custFile.setCreateTime(new Date());
                        custFile.setUpdateTime(new Date());
                        list.add(custFile);
                    }
                    Map map = new HashedMap();
                    map.put("custId",userInfo.getUserId());
                    map.put("companyCode",userInfo.getCompanyCode());
                    map.put("productId",entCustFile.getProductId());
                    EntCustProductApply cpa = custProductApplyMapper.selectProductApplyByMap(map);
                    if(null != cpa){
                        if(cpa.getStatus() == 6){

                        }else {
                            map.put("status",(short)2);
                            int r = custProductApplyMapper.updateStatusByMap(map);
                        }
                    }
                }
            }else {//1对多（fileType--fileUrl）
                for (int i=0;i<fileUrlList.length;i++) {
                    if(fileUrlList[i] == null || fileUrlList[i].isEmpty()) {
                        continue;
                    }
                    EntCustFile custFile = new EntCustFile();
                    custFile.setId(CreateIDUtil.getId());
                    custFile.setStatus((short)1);
                    custFile.setCompanyCode(UserContextUtil.getUserInfo().getCompanyCode());
                    custFile.setCustId(entCustFile.getCustId());
                    custFile.setFileType(fileList[0]);
                    custFile.setFileUrl(fileUrlList[i]);
                    custFile.setProductId(entCustFile.getProductId());
                    custFile.setCreateTime(new Date());
                    custFile.setUpdateTime(new Date());
                    list.add(custFile);
                }
            }
        }


        if (list != null && !list.isEmpty()) {
            if (file.size() == 0) {
                row = custFileMapper.insertSelective(list);
            } else {
                row = custFileMapper.deleteCustomerFile(param);
                custFileMapper.insertSelective(list);
            }
        }


        if(row>0){
            respMap.put("flag",true);
            respMap.put("msg","message.system.operation.success");
            return respMap;
        }
        respMap.put("flag",false);
        respMap.put("msg","message.system.operation.fail");
        return respMap;
    }

    @Override
    public Map updateCustApplyStatus(Map map) throws Exception {
        Map respMap = new HashMap();
        UserInfo userInfo = UserContextUtil.getUserInfo();
        map.put("custId",userInfo.getUserId());
        map.put("companyCode",userInfo.getCompanyCode());
        EntCustProductApply cpa = custProductApplyMapper.selectProductApplyByMap(map);
        if(null != cpa){
            if(cpa.getStatus() == 6){
                respMap.put("flag",true);
                respMap.put("msg","message.system.operation.success");
                return respMap;
            }else {
                map.put("status",(short)5);
                int row = custProductApplyMapper.updateStatusByMap(map);
                if(row>0){
                    respMap.put("flag",true);
                    respMap.put("msg","message.system.operation.success");
                    return respMap;
                }
            }
        }
        respMap.put("flag",false);
        respMap.put("msg","message.system.operation.fail");
        return respMap;
    }

    @Override
    public EntProduct getProductVideo(String productId) throws Exception {
        UserInfo userInfo = UserContextUtil.getUserInfo();
        //ResponseEntity responseEntity = restTemplate.getForEntity(AppFeginUtil.SYSTEMWEB_API_PRODUCTVIDEO_API+"?id="+productId,String.class,tMap);
        //Map respMap = (Map) responseEntity.getBody();
        EntProduct product = custFileMapper.selectProductById(productId);
        Map map = new HashMap();
        map.put("productDetailId",productId);
        List<EntProductVideo> videoList = custFileMapper.selectVideoByMap(map);
        //移除身份证正反面 人脸认证 idcardFront idcardBack faceVerify
        for(int i=0;i<videoList.size();i++){
            String v = videoList.get(i).getVideoType();
            if(v.equals("idcardFront") || v.equals("idcardBack") || v.equals("faceVerify")){
                videoList.remove(videoList.get(i));
            }
        }
        List list = new ArrayList();
        for (EntProductVideo productVideo:videoList) {
            String vidoTypeHH = dictService.getDictNameByDictItemAndDetailCode("videoData",productVideo.getVideoType());
            productVideo.setVideoTypeHH(vidoTypeHH);
            productVideo.setOperation(0);
            list.add(productVideo.getVideoType());
        }
        //String[] fileList = fileType.split(",");
        Map param = new HashMap();
        param.put("custId",userInfo.getUserId());
        param.put("list", list);
        param.put("productId", productId);
        param.put("companyCode", userInfo.getCompanyCode());
        List<EntCustFile> custFileList = custFileMapper.findCustFile(param);
        for (EntProductVideo productVideo:videoList){
            if(custFileList != null && custFileList.size() > 0){
                for (EntCustFile custFile:custFileList){
                    if(null == custFile || null == custFile.getFileType() || "".equals(custFile.getFileType())) {
                        continue;
                    }
                    if(productVideo.getVideoType().equals(custFile.getFileType())){
                        productVideo.setOperation(1);//1代表已经上传 0 未上传
                    }
                }
            }else {
                break;
            }
        }
        product.setVideoList(videoList);
        return product;
    }

    @Override
    public EntCustProductApply getVideo() throws Exception {
        UserInfo userInfo = UserContextUtil.getUserInfo();
        Map map = new HashMap();
        map.put("custId",userInfo.getUserId());
        map.put("companyCode",userInfo.getCompanyCode());
        //获取用户授信成功后的产品id
        EntCustProductApply cpa = custProductApplyMapper.selectProductIdByMap(map);
        //查询是否有影像资料
        Map vmap = new HashMap();
        vmap.put("productId",null==cpa.getProductId()?"":cpa.getProductId());
        List<EntProductVideo> productVideoList = custProductApplyMapper.selectProductVideoByProdictId(vmap);
        if(productVideoList != null && productVideoList.size() > 0){
            cpa.setHaveVideo(1);//是否有影像资料 1有 0无
        }else {
            cpa.setHaveVideo(0);
        }
        return cpa;
    }


}