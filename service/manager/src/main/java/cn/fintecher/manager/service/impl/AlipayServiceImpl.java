package cn.fintecher.manager.service.impl;

import cn.fintecher.manager.mapper.SysParaMapper;
import cn.fintecher.manager.service.AlipayService;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @支付宝支付管理@
 * Create on : 2018年09月11日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:gaozhidong
 * @version 1.0
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@Service
public class AlipayServiceImpl implements AlipayService {

    @Resource
    private SysParaMapper  sysParaMapper;
    /**
     * 支付宝支付(SDK版本)
     * @param map
     * @return
     */
    @Transactional
    @Override
    public Map alipaySDKPayment(Map<String,Object> map) throws Exception{
        Map<String,Object> resultMap=new HashedMap();
        /*****支付参数设置*****/
        List<String> paraNameList = new ArrayList<>();
        paraNameList.add("alipay_public_key");//支付宝公钥
        paraNameList.add("alipay_signtype");//支付宝RSA2
        paraNameList.add("alipay_url");//支付宝请求网关地址
        paraNameList.add("return_url");//支付宝页面跳转同步通知页面路径
        paraNameList.add("notify_url");//支付宝服务器异步通知页面路径
        paraNameList.add("rsa_private_key");//支付宝私钥pkcs8格式的
        paraNameList.add("appId");//支付宝商户appid
        Map<String,String> sysParaMap=getSysPara(paraNameList,map.get("companyCode").toString());
        if(sysParaMap.size()==0){
            resultMap.put("flag",false);
            resultMap.put("mag","没有配置支付参数！");
            return  resultMap;
        }

        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = map.get("transactionId").toString();//交易订单id
        // 订单名称，必填
        String subject = map.get("transactionName").toString();//交易订单名称
        // 付款金额，必填
        String total_amount=map.get("transactionTotalAmount").toString();
        /**************************************************************测试写死**********************************************************************/
        // TODO: 2018/7/9
        //total_amount="0.01";
        /*******************************************************************************************************************************************/
        // 商品描述，可空
        String body = "";
        // 超时时间 可空
        String timeout_express="2m";
        // 销售产品码 必填
        String product_code="QUICK_MSECURITY_PAY";

        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        AlipayClient client = new DefaultAlipayClient(sysParaMap.get("alipay_url"), sysParaMap.get("appId"), sysParaMap.get("rsa_private_key"), "json", "UTF-8", sysParaMap.get("alipay_public_key"),sysParaMap.get("alipay_signtype"));
//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(out_trade_no);
        model.setTimeoutExpress(timeout_express);
        model.setTotalAmount(total_amount);
        model.setProductCode(product_code);
        request.setBizModel(model);
        request.setNotifyUrl(sysParaMap.get("notify_url"));
        // form表单生产
        String form = "";
        // 调用SDK生成表单
        form =client.sdkExecute(request).getBody();
        resultMap.put("form",form);
        //是否使用积分支付
        resultMap.put("flag",true);
        return resultMap;
    }



    /**
     * 获取参数集合
     * @param paraNameList 参数名集合
     *@param companyCode  公司编码
     * @return  Map
     */
    private Map<String,String> getSysPara(List<String>paraNameList,String companyCode){
        Map<String,Object> paramMap=new HashedMap();
        Map<String,String> resultMap=new HashedMap();
        paramMap.put("paraName",paraNameList);
        paramMap.put("companyCode",companyCode);
        List<Map>sysParaList=sysParaMapper.getSysParaByName(paramMap);
        for (int i=0;i<sysParaList.size();i++){
            resultMap.put(sysParaList.get(i).get("para_name").toString(),sysParaList.get(i).get("para_value").toString());
        }
        return  resultMap;
    }

}
