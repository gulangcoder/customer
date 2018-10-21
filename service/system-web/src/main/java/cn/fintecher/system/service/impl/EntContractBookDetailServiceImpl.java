package cn.fintecher.system.service.impl;

import cn.fintecher.common.contract.EntContractBookDetail;
import cn.fintecher.system.client.ManagerFeignUtil;
import cn.fintecher.system.mapper.EntContractBookDetailMapper;
import cn.fintecher.system.mapper.EntContractTempletMapper;
import cn.fintecher.system.mapper.SysParaMapper;
import cn.fintecher.system.model.EntContractTemplet;
import cn.fintecher.system.model.SysPara;
import cn.fintecher.system.service.EntContractBookDetailService;
import cn.fintecher.util.*;
import cn.fintecher.util.redis.RedisKeyConstants;
import cn.fintecher.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月05日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@Service
public class EntContractBookDetailServiceImpl implements EntContractBookDetailService {

    @Autowired
    private EntContractBookDetailMapper entContractBookDetailMapper;

    @Autowired
    private EntContractTempletMapper entContractTempletMapper;

    @Autowired
    private SysParaMapper sysParaMapper;

    @Autowired
    RestTemplate restTemplate;


    /**
     * 查询数据列表
     * @param param
     * @return
     */
    @Override
    public List<Map> getList(Map param) throws Exception{
//        Map param = new HashMap();
//        param.put("companyCode",companyCode);
//        param.put("contractBookNo",contractBookNo);
//        param.put("contractTitle",contractTitle);
//        param.put("contractType",contractType);
//        param.put("customerName",customerName);
//        param.put("borrowNo",borrowNo);
//        param.put("productName",productName);
        List<Map> list = entContractBookDetailMapper.findList(param);
        return list;
    }

    @Override
    public EntContractBookDetail getById(String id)throws Exception {
        return entContractBookDetailMapper.selectByPrimaryKey(id);
    }


    /**
     * 签订模板
     * @param companyCode
     * @param custId
     * @param custName
     * @param custCardNo
     * @param productId
     * @return
     */
    @Override
    public Map saveContractBookDetail(String companyCode, String custId, String custName, String custCardNo, String productId,String contractType) {
        Map respMap = new HashMap();
        //查询合同模板
        Map param = new HashMap();
        param.put("companyCode",companyCode);
        param.put("contractType",contractType);
        param.put("status",1);
        List<Map> templets =entContractTempletMapper.findList(param);
        if(templets==null||templets.size()<1){
            respMap.put("flag",false);
            respMap.put("msg","未查询到有效的合同模板");
            return respMap;
        }

        //生成合同签订编码
        String time = DateConversionUtil.getCurrentTime(DateConversionUtil.STYLE_3);
        String code = new GUIDUtil().toString();
        String lastCode = code.substring(code.length()-4,code.length());
        String contractBookNo = contractType+time+lastCode;

        EntContractBookDetail entContractBookDetail = new EntContractBookDetail();
        entContractBookDetail.setId(CreateIDUtil.getId());
        entContractBookDetail.setCompanyCode(companyCode);
        //TODO:合同模板中的用户信息
        entContractBookDetail.setContent(templets.get(0).get("content").toString());
        entContractBookDetail.setContractBookNo(contractBookNo);
        entContractBookDetail.setContractTemplId(templets.get(0).get("id").toString());
        entContractBookDetail.setContractTitle(templets.get(0).get("title").toString());
        entContractBookDetail.setContractType(contractType);
        entContractBookDetail.setCreateTime(new Date());
        entContractBookDetail.setCustomerCardNo(custCardNo);
        entContractBookDetail.setCustomerName(custName);
        entContractBookDetail.setCustomerId(custId);
        entContractBookDetail.setProductId(productId);
        int row = entContractBookDetailMapper.insertSelective(entContractBookDetail);
        if(row>0){
            respMap.put("flag",true);
            respMap.put("msg","保存成功");
            return respMap;
        }
        respMap.put("flag",false);
        respMap.put("msg","保存失败");
        return respMap;
    }


    @Override
    public List<String> getListByIds(List<String>ids)throws Exception{
        return entContractBookDetailMapper.getListByIds(ids);
    }

    /**
     * 生成合同内容
     * @return
     */
    @Override
    /**
     * 返回 服务器类型 - 服务器地址: OSS:xxx.xxxx.xxx； Ftp：xxx.xxx.xxx.xx
     * 这个方法暴露给APP service使用，方法内部唯一逻辑 调用manage并保存URL到Db
     * */
    //public Map generateBookContract(String companycode,String fileType,List serverList,Map paraMap) {
    public Map generateBookContract(Map data)throws Exception {
        Map respMap = new HashMap();
       //查询企业合同模板
        Map param = new HashMap();
        param.put("companyCode",data.get("companyCode"));
        param.put("contractType",data.get("contractType"));
        List<Map> ets=entContractTempletMapper.findList(param);
        if(ets==null||ets.size()<1){
            respMap.put("flag",false);
            respMap.put("msg","message.contractTempl.not.existed");
            return respMap;
        }
        //TODO:替换变量
        String content = ets.get(0).get("content").toString();
        data.put("content",content);
        //TODO:查询上传文件的参数
        Map spMap = new HashMap();
        spMap.put("companyCode",data.get("companyCode"));
        spMap.put("paraName","bucketName");
        String bucketName=sysParaMapper.getParaValueByParaName(spMap);
        spMap.put("paraName","accessKeyId");
        String accessKeyId=sysParaMapper.getParaValueByParaName(spMap);
        spMap.put("paraName","endpoint");
        String endpoint=sysParaMapper.getParaValueByParaName(spMap);
        spMap.put("paraName","accessKeySecret");
        String accessKeySecret=sysParaMapper.getParaValueByParaName(spMap);
//        data.put("bucketName",bucketName);
//        data.put("accessKeyId",accessKeyId);
//        data.put("endpoint",endpoint);
//        data.put("accessKeySecret",accessKeySecret);
        //企业编号、文件类型、上传的服务器类型List、变量Map
        //调用manager接口
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("bucketName", bucketName);
        postParameters.add("accessKeyId", accessKeyId);
        postParameters.add("endpoint", endpoint);
        postParameters.add("accessKeySecret", accessKeySecret);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, Object>> r = new HttpEntity<>(postParameters, headers);
        ResponseEntity<Map> info = restTemplate.postForEntity(ManagerFeignUtil.MANAGER_API_CONTRACTUPLOAD_API,r,Map.class);

        EntContractBookDetail detail = new EntContractBookDetail();
        detail.setId(CreateIDUtil.getId());
        detail.setCompanyCode(String.valueOf(data.get("companyCode")));
        detail.setProductId(String.valueOf(data.get("productId")));
        detail.setCustomerId(String.valueOf(data.get("customerId")));
        detail.setCustomerName(String.valueOf(data.get("customerName")));
        detail.setCustomerCardNo(String.valueOf(data.get("customerCardNo")));
        detail.setContractType(String.valueOf(data.get("contractType")));
        detail.setContractTitle(ets.get(0).get("title").toString());
        //保存上传地址
        detail.setContent(info.getBody().get("urls").toString());
        int row = entContractBookDetailMapper.insertSelective(detail);
        if(row>0){
            respMap.put("flag",true);
            respMap.put("msg","message.contract.book.success");
            return respMap;
        }
        respMap.put("flag",false);
        respMap.put("msg","message.contract.book.fail");
        return respMap;
    }

    @Override
    public Map generateBookContract(EntContractBookDetail entContractBookDetail) throws Exception {
        Map respMap = new HashMap();
        //判断模板是否存在
        Map param = new HashMap();
        param.put("companyCode",entContractBookDetail.getCompanyCode());
        param.put("contractType",entContractBookDetail.getContractType());
        List<Map> list = entContractTempletMapper.findList(param);
        if(list==null||list.size()<1){
            respMap.put("flag",false);
            respMap.put("msg","未查询到模板信息");
            return respMap;
        }
        //TODO:查询上传文件配置:oss上传还是Ftp等
//        param.put("severList",severList);
        //查询oss上传
        String bucketName = getParaByCompanyAndKey(entContractBookDetail.getCompanyCode(), "oss_bucketName");
        String endpoint = getParaByCompanyAndKey(entContractBookDetail.getCompanyCode(), "oss_endpoint");
        String accessKeyId = getParaByCompanyAndKey(entContractBookDetail.getCompanyCode(), "oss_accessKeyId");
        String accessKeySecret = getParaByCompanyAndKey(entContractBookDetail.getCompanyCode(), "oss_accessKeySecret");

        param=new HashMap();
        param.put("bucketName",bucketName);
        param.put("endpoint",endpoint);
        param.put("accessKeyId",accessKeyId);
        param.put("accessKeySecret",accessKeySecret);

        String content = entContractBookDetail.getContent();
        //TODO:判断不需要替换变量的协议  如:注册协议  是否上传
        if(entContractBookDetail.getContractType().equals("register")){
            param.put("content",list.get(0).get("content"));
        }else{
            //TODO:替换变量
            content.replaceAll("#customerName#",entContractBookDetail.getCustomerName());
            param.put("content",content);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<Map<String, Object>> r = new HttpEntity<>(param, headers);
        ResponseEntity<Map> info = restTemplate.postForEntity(ManagerFeignUtil.MANAGER_API_CONTRACTUPLOAD_API,r,Map.class);
        Map upload=info.getBody();
        if(null!=upload&&!StringUtil.isEmpty(String.valueOf(upload.get("appUrl")))){
            entContractBookDetail.setContent(String.valueOf(upload.get("appUrl")));
            entContractBookDetail.setId(CreateIDUtil.getId());
            entContractBookDetail.setCreateTime(new Date());
//            StringStringUtil.getPinYinHeadChar(entContractBookDetail.getCustomerName()).toUpperCase();
//            entContractBookDetail.set
            int row =entContractBookDetailMapper.insertSelective(entContractBookDetail);
            if(row>0){
                respMap.put("flag",true);
                respMap.put("msg","合同签订成功");
                return respMap;
            }
        }
        respMap.put("flag",false);
        respMap.put("msg","合同签订失败");
        return respMap;
    }

    @Override
    public List<EntContractTemplet> getProtocol(Map parm) {
        return entContractTempletMapper.getProtocol(parm);
    }


    public String getParaByCompanyAndKey(String companyCode, String key){
        String paraValue = "";
        Map sysPara = RedisUtil.get(RedisKeyConstants.SYS_PARA + key + "_" + companyCode);
        if (sysPara == null) {
            Map param = new HashMap();
            param.put("paraName",key);
            param.put("companyCode",companyCode);
            List<SysPara> sysParaList = sysParaMapper.findList(param);
            paraValue = sysParaList.get(0).getParaValue();
        }else{
            paraValue = String.valueOf(sysPara.get("paraValue"));
        }
        return paraValue;
    }
}