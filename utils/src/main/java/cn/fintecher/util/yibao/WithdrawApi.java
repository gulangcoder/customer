package cn.fintecher.util.yibao;

import com.cfca.util.pki.PKIException;
import com.cfca.util.pki.api.CertUtil;
import com.cfca.util.pki.api.KeyUtil;
import com.cfca.util.pki.api.SignatureUtil;
import com.cfca.util.pki.cert.X509Cert;
import com.cfca.util.pki.cipher.JCrypto;
import com.cfca.util.pki.cipher.JKey;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description :提现api
 * Create on : 2018年07月10日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:zhangtao
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
public class WithdrawApi {
    private static com.cfca.util.pki.cipher.Session tempsession = null;
    private final static Logger logger = LoggerFactory.getLogger(WithdrawApi.class);

    /**
     * 易宝提现接口
     * @param xml 请求报文
     * @param host 请求接口地址
     * @param hmacKey 密钥
     * @param pfxPath 证书路径
     * @return
     * @throws Exception
     */
    public static Map transferParamResolver(String xml, String host, String hmacKey, String pfxPath, String pfxPassword) throws Exception {
        Map returnMap = new HashMap();
        //调用体现接口，发送报文
        Map result = new LinkedHashMap();
        Map xmlMap = new LinkedHashMap();
        Map xmlBackMap = new LinkedHashMap();
        String ret_Code = "";//系统返回码
        String bank_Status = "";//银行状态码
        String error_Msg = "";//错误信息
        String r1_Code = "";//打款状态码
        String s = "cmd,mer_Id,batch_No,order_Id,amount,account_Number,hmacKey";
        String[] digestValues = s.split(",");//request.getParameter("digest").split(",");
        String ss = "cmd,ret_Code,mer_Id,batch_No,total_Amt,total_Num,r1_Code,hmacKey";
        String[] backDigestValues = ss.split(",");
        //第一步:将请求的数据和商户自己的密钥拼成一个字符串,
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (Exception e) {
        }
        Element rootEle = document.getRootElement();
        String cmdValue = rootEle.elementText("cmd");
        List list = rootEle.elements();
        for (int i = 0; i < list.size(); i++) {
            Element ele = (Element) list.get(i);
            String eleName = ele.getName();
            if (!eleName.equals("list")) {
                xmlMap.put(eleName, ele.getText().trim());
            } else {
                continue;
            }
        }

        String hmacStr = "";
        for (int i = 0; i < digestValues.length; i++) {
            if (digestValues[i].equals("hmacKey")) {
                hmacStr = hmacStr + hmacKey;
                continue;
            }
            hmacStr = hmacStr + xmlMap.get(digestValues[i]);
        }
        logger.info("签名之前的源数据为---||" + hmacStr + "||");

        //下面用数字证书进行签名
        String ALGORITHM = SignatureUtil.SHA1_RSA;
        JCrypto jcrypto = null;
        if (tempsession == null) {
            try {
                //初始化加密库，获得会话session
                //多线程的应用可以共享一个session,不需要重复,只需初始化一次
                //初始化加密库并获得session。
                //系统退出后要jcrypto.finalize()，释放加密库
                jcrypto = JCrypto.getInstance();
                jcrypto.initialize(JCrypto.JSOFT_LIB, null);
                tempsession = jcrypto.openSession(JCrypto.JSOFT_LIB);
            } catch (Exception ex) {
                logger.info(ex.toString());
            }
        }
        JKey jkey = null;
        SignatureUtil signUtil = null;
        X509Cert cert = null;

        try {
            jkey = KeyUtil.getPriKey(pfxPath, pfxPassword);
        } catch (PKIException e1) {
            e1.printStackTrace();
        }
        try {
            cert = CertUtil.getCert(pfxPath, pfxPassword);
        } catch (PKIException e1) {
            e1.printStackTrace();
        }
        System.out.println(cert.getSubject());
        X509Cert[] cs = new X509Cert[1];
        cs[0] = cert;
        String signMessage = "";
        try {
            // 第二步:对请求的串进行MD5对数据进行签名
            String yphs;
            yphs = YbaoSignUtil.hmacSign(hmacStr);
            signUtil = new SignatureUtil();
            byte[] b64SignData;
            // 第三步:对MD5签名之后数据调用CFCA提供的api方法用商户自己的数字证书进行签名
            b64SignData = signUtil.p7SignMessage(true, yphs.getBytes(), ALGORITHM, jkey, cs, tempsession);
            if (jcrypto != null) {
                jcrypto.finalize(com.cfca.util.pki.cipher.JCrypto.JSOFT_LIB, null);
            }
            signMessage = new String(b64SignData, "UTF-8");
        } catch (Exception e) {
        }
        logger.info("经过md5和数字证书签名之后的数据为--->" + signMessage);
        Element r = rootEle.element("hmac");
        r.setText(signMessage);
        result.put("xml", xml);
        document.setXMLEncoding("GBK");
        logger.info("完整xml请求报文--->" + document.asXML());

        //第四步:发送https请求
        String responseMsg = CallbackUtils.httpRequest(host, document.asXML(), "POST", "gbk", "text/xml ;charset=gbk", false);
        System.out.println(
                "<html><body><textarea rows=\"23\" cols=\"120\" name=\"xml\" id=\"xml\">" +
                        responseMsg +
                        "</textarea></body></html>");
        logger.info("服务器响应xml报文--->" + responseMsg);
        try {
            document = DocumentHelper.parseText(responseMsg);
        } catch (Exception e) {
        }
        rootEle = document.getRootElement();
        cmdValue = rootEle.elementText("hmac");
        //第五步:对服务器响应报文进行验证签名
        boolean sigerCertFlag = false;
        if (cmdValue != null) {
            try {
                sigerCertFlag = signUtil.p7VerifySignMessage(cmdValue.getBytes(), tempsession);
            } catch (PKIException e1) {
                e1.printStackTrace();
            }
            String backmd5hmac = xmlBackMap.get("hmac") + "";
            if (sigerCertFlag) {
                logger.info("证书验签成功--->" + "证书验签成功");
                try {
                    backmd5hmac = new String(signUtil.getSignedContent());
                } catch (PKIException e1) {
                    e1.printStackTrace();
                }
                logger.info("证书验签获得的MD5签名数据为--->" + backmd5hmac);
                try {
                    logger.info("证书验签获得的证书dn为--->" + new String(signUtil.getSigerCert()[0].getSubject()));
                } catch (PKIException e1) {
                    e1.printStackTrace();
                }
                //第六步.将验签出来的结果数据与自己针对响应数据做MD5签名之后的数据进行比较是否相等
                Document backDocument = null;
                try {
                    backDocument = DocumentHelper.parseText(responseMsg);
                } catch (Exception e) {
                    System.out.println(e);
                }
                Element backRootEle = backDocument.getRootElement();
                List backlist = backRootEle.elements();
                for (int i = 0; i < backlist.size(); i++) {
                    Element ele = (Element) backlist.get(i);
                    String eleName = ele.getName();
                    if (!eleName.equals("list")) {
                        xmlBackMap.put(eleName, ele.getText().trim());
                    } else {
                        continue;
                    }
                }
                String backHmacStr = "";
                for (int i = 0; i < backDigestValues.length; i++) {
                    if (backDigestValues[i].equals("hmacKey")) {
                        backHmacStr = backHmacStr + hmacKey;
                        continue;
                    }
                    String tempStr = (String) xmlBackMap.get(backDigestValues[i]);
                    backHmacStr = backHmacStr + ((tempStr == null) ? "" : tempStr);
                }
                String newmd5hmac = YbaoSignUtil.hmacSign(backHmacStr);
                logger.info("提交返回源数据为--->" + backHmacStr);
                logger.info("经过md5签名后的验证返回hmac为--->" + newmd5hmac);
                logger.info("提交返回的hmac为--->" + backmd5hmac);
                if (newmd5hmac.equals(backmd5hmac)) {
                    System.out.println("md5验签成功");
                    //第七步:判断该证书DN是否为易宝
                    try {
                        if (signUtil.getSigerCert()[0].getSubject().toUpperCase().indexOf("OU=YEEPAY,") > 0) {
                            logger.info("证书DN是易宝的--->" + "证书DN是易宝的");
                            ret_Code = rootEle.elementText("ret_Code");//系统返回吗
                            bank_Status = rootEle.elementText("bank_Status");//银行状态码
                            error_Msg = rootEle.elementText("error_Msg");//错误信息
                            r1_Code = rootEle.elementText("r1_Code");//打款状态
                            returnMap.put("ret_Code", ret_Code);
                            returnMap.put("bank_Status", bank_Status);
                            returnMap.put("error_Msg", error_Msg);
                            returnMap.put("r1_Code", r1_Code);
                            returnMap.put("responseMsg", responseMsg);
                        } else {
                            logger.info("证书DN不是易宝的--->" + "证书DN不是易宝的");
                        }
                    } catch (PKIException e) {
                        e.printStackTrace();
                    }
                } else {
                    logger.info("md5验签失败--->" + "md5验签失败");
                }
            } else {
                logger.info("证书验签失败--->" + "证书验签失败");
            }
        }
        return returnMap;
    }

    /**
     * 易宝提现回调接口
     *
     * @param hmacKey 密钥
     * @param pfxPath 证书路径
     * @param responseMsg 返回的xml
     * @return
     */
    public static Map ybWithdrawalCallbackInfo(String hmacKey, String pfxPath, String responseMsg, String pfxPassword) {
        Map result = new LinkedHashMap();
        Map xmlBackMap = new LinkedHashMap();
        logger.info("服务器响应xml报文--->" + responseMsg);
        Document document = null;
        try {
            document = DocumentHelper.parseText(responseMsg);
        } catch (Exception e) {
        }
        Element rootEle = document.getRootElement();
        String cmdValue = rootEle.elementText("hmac");

        //对服务器响应报文进行验证签名
        String ALGORITHM = SignatureUtil.SHA1_RSA;
        JCrypto jcrypto = null;
        if (tempsession == null) {
            try {
                //初始化加密库，获得会话session
                //多线程的应用可以共享一个session,不需要重复,只需初始化一次
                //初始化加密库并获得session。
                //系统退出后要jcrypto.finalize()，释放加密库
                jcrypto = JCrypto.getInstance();
                jcrypto.initialize(JCrypto.JSOFT_LIB, null);
                tempsession = jcrypto.openSession(JCrypto.JSOFT_LIB);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
        JKey jkey = null;
        try {
            jkey = KeyUtil.getPriKey(pfxPath, pfxPassword);
        } catch (PKIException e) {
            e.printStackTrace();
        }
        X509Cert cert = null;
        try {
            cert = CertUtil.getCert(pfxPath, pfxPassword);
        } catch (PKIException e) {
            e.printStackTrace();
        }
        X509Cert[] cs = new X509Cert[1];
        cs[0] = cert;
        boolean sigerCertFlag = false;
        SignatureUtil signUtil = new SignatureUtil();
        if (cmdValue != null) {
            try {
                sigerCertFlag = signUtil.p7VerifySignMessage(cmdValue.getBytes(), tempsession);
            } catch (PKIException e) {
                e.printStackTrace();
            }
            String backmd5hmac = xmlBackMap.get("hmac") + "";
            logger.info("------------------>" + backmd5hmac);
            if (sigerCertFlag) {
                logger.info("证书验签成功");
                try {
                    backmd5hmac = new String(signUtil.getSignedContent());
                    logger.info("证书验签获得的MD5签名数据为----" + backmd5hmac);
                } catch (PKIException e) {
                    e.printStackTrace();
                }
                //将验签出来的结果数据与自己针对响应数据做MD5签名之后的数据进行比较是否相等
                Document backDocument = null;
                try {
                    backDocument = DocumentHelper.parseText(responseMsg);
                } catch (Exception e) {
                    System.out.println(e);
                }
                Element backRootEle = backDocument.getRootElement();
                List backlist = backRootEle.elements();
                for (int i = 0; i < backlist.size(); i++) {
                    Element ele = (Element) backlist.get(i);
                    String eleName = ele.getName();
                    if (!eleName.equals("list")) {
                        xmlBackMap.put(eleName, ele.getText().trim());
                    } else {
                        continue;
                    }
                }
                String backHmacStr = "";
                String[] backDigestValues = "cmd,mer_Id,batch_No,order_Id,status,message,hmacKey".split(",");
                for (int i = 0; i < backDigestValues.length; i++) {
                    if (backDigestValues[i].equals("hmacKey")) {
                        backHmacStr = backHmacStr + hmacKey;
                        continue;
                    }
                    String tempStr = (String) xmlBackMap.get(backDigestValues[i]);
                    backHmacStr = backHmacStr + ((tempStr == null) ? "" : tempStr);
                }
                String newmd5hmac = YbaoSignUtil.hmacSign(backHmacStr);
                logger.info("提交返回源数据为----" + backHmacStr);
                logger.info("经过md5签名后的验证返回hmac为----" + newmd5hmac);
                logger.info("提交返回的hmac为----" + backmd5hmac);
                if (newmd5hmac.equals(backmd5hmac)) {
                    logger.info("md5验签成功----");
                    //判断该证书DN是否为易宝
                    try {
                        if (signUtil.getSigerCert()[0].getSubject().toUpperCase().indexOf("OU=YEEPAY,") > 0) {
                            logger.info("证书DN是易宝的----");
                            //回写易宝xml
                            String ret_Code = "S";
                            StringBuffer str = new StringBuffer();
                            str.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
                            str.append("<data>");
                            str.append("<cmd>TransferNotify</cmd>");
                            str.append("<version>1.1<ersion>");
                            str.append("<mer_Id>" + xmlBackMap.get("mer_Id") + "</mer_Id>");
                            str.append("<batch_No>" + xmlBackMap.get("batch_No") + "</batch_No>");
                            str.append("<order_Id>" + xmlBackMap.get("order_Id") + "</order_Id>");
                            str.append("<ret_Code>"+ret_Code+"</ret_Code>");//这里根据情况传 S 或 F 如果是 S 则不会重发   如果是 F 会重发
                            cmdValue = "TransferNotify" + xmlBackMap.get("mer_Id") + xmlBackMap.get("batch_No") + xmlBackMap.get("order_Id") + ret_Code + hmacKey;
                            String hmac = YbaoSignUtil.hmacSign(cmdValue);
                            str.append("<hmac>" + new String(signUtil.p7SignMessage(true, hmac.getBytes(),ALGORITHM, jkey, cs, tempsession)) + "</hmac>");
                            str.append("</data>");
                            result.put("order_Id", xmlBackMap.get("order_Id"));
                            result.put("status", xmlBackMap.get("status"));
                            result.put("batch_No", xmlBackMap.get("batch_No"));
                            result.put("remark",xmlBackMap.get("message"));
                            result.put("str",str);
                        } else {
                            logger.info("证书DN不是易宝的----");
                            result.put("remark","证书DN不是易宝的");
                        }
                    } catch (PKIException e) {
                        e.printStackTrace();
                        result.put("remark","系统网络异常");
                    }
                } else {
                    logger.info("md5验签失败----");
                    result.put("remark","md5验签失败");
                }
            } else {
                logger.info("证书验签失败----");
                result.put("remark","证书验签失败");
            }
        }else{
            result.put("remark","易宝返回结果为空");
        }
        return result;
    }
}
