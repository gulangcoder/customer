package cn.fintecher.manager.service.impl;

import cn.fintecher.manager.mapper.SysParaMapper;
import cn.fintecher.manager.service.WeChatPayService;
import cn.fintecher.util.WXRequestUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @微信支付管理@
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
public class WeChatPayServiceImpl implements WeChatPayService {

    @Resource
    private SysParaMapper sysParaMapper;
    /**
     * 微信支付
     * @param map
     * @return
     */
    @Transactional
    @Override
    public Map WeChatPaySdk(Map<String,Object> map) throws Exception{
        Map<String,Object> resultMap=new HashedMap();
        //获取支付参数
        List<String> paraNameList = new ArrayList<>();
        paraNameList.add("weixin_api_key");//微信api_key
        paraNameList.add("weixin_appid");//微信appid
        paraNameList.add("weixin_mch_id");//微信mch_id
        paraNameList.add("weixin_notify_url");//weixin服务器异步通知页面路径
        paraNameList.add("weixin_url");//微信统一下单url
        paraNameList.add("weixin_orderquery_url");//微信订单查询url
        Map<String,String> sysParaMap=getSysPara(paraNameList,map.get("companyCode").toString());
        if(sysParaMap.size()==0){
            resultMap.put("flag",false);
            resultMap.put("mag","没有配置支付参数！");
            return  resultMap;
        }

        // 付款金额，必填
        Double total_amount=((Double)map.get("transactionTotalAmount")*100);

        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = map.get("transactionId").toString();//交易订单id
        // 付款金额，必填
        /**************************************************************测试写死**********************************************************************/
        // TODO: 2018/7/9
        //total_amount=1;//1分钱
        /*******************************************************************************************************************************************/

        Map<String,String> param = new HashMap<String,String>();
        param.put("appid", sysParaMap.get("weixin_appid"));
        param.put("mch_id", sysParaMap.get("weixin_mch_id"));
        param.put("nonce_str", WXRequestUtil.NonceStr());
        param.put("body", map.get("transactionName").toString());//支付描述
        param.put("out_trade_no", map.get("transactionId").toString());//商户交易号
        param.put("total_fee", total_amount.intValue()+"");
        param.put("spbill_create_ip", WXRequestUtil.GetIp());//ip
        param.put("notify_url", sysParaMap.get("weixin_notify_url"));//回调地址
        param.put("trade_type", "NATIVE");
        String sign = WXRequestUtil.GetSign(param,sysParaMap.get("weixin_api_key"));
        param.put("sign", sign);
        //调用统一下单接口并解析
        Map<String,String> data = WXRequestUtil.doXMLParse(
                WXRequestUtil.httpsRequest(sysParaMap.get("weixin_url"),"POST",
                        WXRequestUtil.GetMapToXML(param)));
        if(data==null||data.get("return_code")==null){
            resultMap.put("flag",false);
            resultMap.put("mag","请求微信支付异常！");
            return  resultMap;
        }
        //返回状态码 return_code       业务结果 result_code
        if("FAIL".equals(data.get("return_code"))){
            resultMap.put("flag",false);
            resultMap.put("mag",data.get("return_msg"));
            return  resultMap;
        }
        if ("SUCCESS".equals(data.get("return_code"))){
            if ("FAIL".equals(data.get("result_code"))){
                resultMap.put("flag",false);
                resultMap.put("mag",data.get("err_code_des"));
                return  resultMap;
            }
            if ("SUCCESS".equals(data.get("return_code"))){
                Map<String,String> payParam=new HashedMap();
                payParam.put("appid",sysParaMap.get("weixin_appid"));//应用ID
                payParam.put("partnerid",sysParaMap.get("weixin_mch_id"));//商户号
                payParam.put("prepayid",data.get("prepay_id"));//预支付交易会话ID
                payParam.put("package","Sign=WXPay");//扩展字段
                payParam.put("noncestr",WXRequestUtil.NonceStr());//随机字符串
                payParam.put("timestamp",(System.currentTimeMillis()/1000)+"");//时间戳
                payParam.put("sign",WXRequestUtil.GetSign(payParam,sysParaMap.get("weixin_api_key")));//签名
                resultMap.put("flag",true);
                resultMap.put("payParam",payParam);
                return  resultMap;
            }
            else {
                resultMap.put("flag",false);
                resultMap.put("mag","请求微信支付未知错误！");
                return  resultMap;
            }
        }else {
            resultMap.put("flag",false);
            resultMap.put("mag","请求微信支付未知错误！");
            return  resultMap;
        }
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
