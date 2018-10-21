package cn.fintecher.app.service.ocr.impl;

import cn.fintecher.app.mapper.customer.EntCustProductApplyMapper;
import cn.fintecher.app.mapper.customer.EntCustomerFaceMapper;
import cn.fintecher.app.mapper.customer.EntCustomerInfoMapper;
import cn.fintecher.app.mapper.sys.SysParaMapper;
import cn.fintecher.app.model.customer.EntCustProductApply;
import cn.fintecher.app.model.customer.EntCustomerFace;
import cn.fintecher.app.model.customer.EntCustomerInfo;
import cn.fintecher.app.model.sys.SysPara;
import cn.fintecher.app.service.ocr.OcrService;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.http.HttpClientUtil;
import cn.fintecher.util.ocr.AliveCertification;
import cn.fintecher.util.uploadFile.util.UploadFileToOssUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月16日 11:02<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:吴城 <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */

@Service
public class OcrServiceImpl  implements OcrService {
    @Resource
    private SysParaMapper sysParaMapper;

    @Autowired
    private EntCustomerFaceMapper customerFaceMapper;

    @Autowired
    private EntCustProductApplyMapper custProductApplyMapper;

    @Autowired
    private EntCustomerInfoMapper customerInfoMapper;

    @Override
    public String getNonceTicket(Map map) throws Exception {
        String user_id = (String) map.get("user_id");
        String aliveUrl = (String) map.get("url");
        String app_id = (String) map.get("app_id");
        String type = (String) map.get("type");
        String version = (String) map.get("version");
        String companyCode = (String)map.get("companyCode");
        String accessToken = getAccessToken(companyCode);
        String url = aliveUrl+"/api/oauth2/api_ticket";
        String str = AliveCertification.getNonceTicket(url,app_id,accessToken,type,version,user_id);
        return str;
    }

    @Override
    public String getTencentOcrSignTicket(Map ticketMap) throws Exception {
        String aliveUrl = (String) ticketMap.get("url");
        String app_id = (String) ticketMap.get("app_id");
        String type = (String) ticketMap.get("type");
        String version = (String) ticketMap.get("version");
        String companyCode = (String)ticketMap.get("companyCode");
        String accessToken = getAccessToken(companyCode);
        String url = aliveUrl+"/api/oauth2/api_ticket";
        String str = AliveCertification.getTencentOcrSignTicket(url,app_id,accessToken,type,version);
        /* JSONObject dataJson = JSON.parseObject(str);
        String code = dataJson.get("code").toString();
        if(!"0".equals(code)){
            return "TencentOcrError";
        }
        Map ticketsMap = (Map)((List)dataJson.get("tickets")).get(0);
        ticketsMap.get("value").toString()*/
        return str;
    }

    @Override
    public boolean validation( Map map) throws Exception {
        String companyCode = (String)map.get("companyCode");
        String sign_ticket = getSignTicket(companyCode);
        List<String> list = new ArrayList<String>();
       String user_id= map.get("user_id")+"";
       String nonceStr= map.get("nonceStr")+"";
       String version= map.get("version")+"";
       String app_id= map.get("app_id")+"";
       String app_sign= map.get("app_sign")+"";
        list.add(app_id);
        list.add(sign_ticket);
        list.add(user_id);
        list.add(nonceStr);
        list.add(version);
        String sign = AliveCertification.sign(list,sign_ticket);
        if(sign.equals(app_sign)){
            return true;
        }
        return false;
    }

    @Override
    public String getAccessToken(String companyCode) throws Exception {
        Map param =new HashMap();
        param.put("paraName","access_token");
        param.put("companyCode",companyCode);
        return sysParaMapper.getParaValueByParaName(param);
    }

    public String getSignTicket(String companyCode)throws Exception {
        Map param =new HashMap();
        param.put("paraName","sign_ticket");
        param.put("companyCode",companyCode);
        return sysParaMapper.getParaValueByParaName(param);
    }

    @Transactional
    @Override
    public Map saveCustomerFace(Map pmap) throws Exception {
        Map resultMap = new HashMap();
        String singStr =  pmap.get("validationSign").toString();
        String nonceStr =  pmap.get("nonce").toString();
        String orderNo =  pmap.get("orderNo").toString();
        String appId =  pmap.get("appId").toString();
        String faceResultUrl =  pmap.get("faceResultUrl").toString();
        String singVersion =  pmap.get("singVersion").toString();
        String bucketName =  pmap.get("bucketName").toString();
        String endpoint =  pmap.get("endpoint").toString();
        String accessKeyId =  pmap.get("accessKeyId").toString();
        String accessKeySecret =  pmap.get("accessKeySecret").toString();
        String path =  pmap.get("path").toString();
        String productId=  pmap.get("productId").toString();

        //获取人脸识别的结果信息
        Map map = getFaceResult(appId,singVersion,"1",nonceStr,singStr,orderNo,faceResultUrl);
        if(!(boolean)map.get("flag")){
            resultMap.put("flag",false);
            resultMap.put("msg",map.get("msg").toString());
            return resultMap;
        }
        //人脸的照片
        String photo = map.get("photo").toString();
        String photoPath = "";
        if(StringUtils.isNotEmpty(photo)){
            Map<String, String> srcMap1 = new HashMap<>();
            srcMap1.put("card",photo);
            Map phoneMap = UploadFileToOssUtil.uploadToOSS(srcMap1,path,null,bucketName,endpoint,accessKeyId,accessKeySecret);
            photoPath = phoneMap.get("card").toString();
           // photoPath = ImgUtils.saveBase64ImgLocal(photo);
        }
        //人脸的视频
        String video = map.get("video").toString();
        String videoPath = "";
        if(StringUtils.isNotEmpty(video)){
            Map<String, String> srcMap2 = new HashMap<>();
            srcMap2.put("card",video);
            Map phoneMap = UploadFileToOssUtil.uploadVIdeoToOSS(srcMap2,path,null,bucketName,endpoint,accessKeyId,accessKeySecret);
            videoPath = phoneMap.get("card").toString();
            //videoPath = ImgUtils.saveBase64VideoLocal(video);
        }
        //活体检测得分
        String liveRate = map.get("liveRate").toString();
        //人脸比对得分
        String similarity = map.get("similarity").toString();
        //先删除 再添加
        String custId = UserContextUtil.getUserInfo().getUserId();
        String companyCode = UserContextUtil.getUserInfo().getCompanyCode();
        Map dmap = new HashMap();
        dmap.put("custId",custId);
        dmap.put("companyCode",companyCode);
        int row = customerFaceMapper.deleteFaceByMap(dmap);
        EntCustomerFace customerFace = new EntCustomerFace();
        customerFace.setId(CreateIDUtil.getId());
        customerFace.setCustId(custId);
        customerFace.setCompanyCode(companyCode);
        customerFace.setFacePhoto(photoPath);
        customerFace.setFaceVideo(videoPath);
        customerFace.setFaceLiveRate(liveRate);
        customerFace.setFaceSimilarity(similarity);
        customerFace.setFaceStatus("1");
        customerFace.setCreateTime(new Date());
        int addRow = customerFaceMapper.insertSelective(customerFace);
        //修改实名认证状态为已认证 0 已认证 1 未认证
        EntCustomerInfo customerInfo = new EntCustomerInfo();
        customerInfo.setCustId(custId);
        customerInfo.setRealnameVerify((short)0);
        int updRow = customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
        //更改步骤
        Map respMap = new HashMap();
        map.put("custId",custId);
        map.put("companyCode",companyCode);
        map.put("productId",productId);
        EntCustProductApply cpa = custProductApplyMapper.selectProductApplyByMap(map);
        if(null != cpa){
            if(cpa.getStatus() == 6){

            }else {
                map.put("status",(short)6);
                int r = custProductApplyMapper.updateStatusByMap(map);
            }
        }
        resultMap.put("flag",true);
        resultMap.put("msg",map.get("msg").toString());
        return resultMap;
    }


    public  Map getFaceResult(String appId,String singVersion,String file, String nonce,String sign,String orderNo,String faceResultUrl) throws Exception{
        Map result = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append(faceResultUrl).append("?")
                .append("app_id=").append(appId)
                .append("&nonce=").append(nonce)
                .append("&order_no=").append(orderNo)
                .append("&version=").append(singVersion)
                .append("&sign=").append(sign)
                .append("&get_file=").append(file);
        LoggerCommon.info(this.getClass(),"订单号："+orderNo);
        LoggerCommon.info(this.getClass(),"人脸识别请求："+sb.toString());
        String data = HttpClientUtil.getInstance().sendHttpGet(sb.toString());
        JSONObject dataJson = JSON.parseObject(data);
        String code = dataJson.get("code").toString();
        if(!"0".equals(code)){
            result.put("flag",false);
            result.put("msg","message.face.error");
            return result;
        }
        Map ticketsMap = (Map)dataJson.get("result");
        //人脸的图片信息
        String photo = ticketsMap.get("photo").toString();
        result.put("photo", StringUtils.isEmpty(photo)?"":photo);
        //人脸的视屏信息
        String video = ticketsMap.get("video")==null?"":ticketsMap.get("video").toString();
        result.put("video",StringUtils.isEmpty(video)?"":video);
        String similarity = ticketsMap.get("similarity").toString();
        result.put("similarity",StringUtils.isEmpty(similarity)?"":similarity);
        String liveRate = ticketsMap.get("liveRate").toString();
        result.put("liveRate",StringUtils.isEmpty(liveRate)?"":liveRate);
        result.put("flag",true);
        result.put("msg","message.system.successMessage");
        return result;
    }
}
