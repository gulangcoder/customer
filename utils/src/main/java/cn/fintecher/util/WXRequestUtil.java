package cn.fintecher.util;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.DocumentHelper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.util.*;

/*
 * 用户发起统一下单请求
 * 作者：gaozhidong
 */
public class WXRequestUtil {

    public static void main(String[] args) {
        SendPayment("苹果","20170106113324",1,"1");
    }

    /*
     * 发起支付请求
     * body	商品描述
     * out_trade_no	订单号
     * total_fee	订单金额		单位  元
     * product_id	商品ID
     */
    public static Map<String,String> SendPayment(String body,String out_trade_no,double total_fee,String product_id){
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String xml = WXParamGenerate(body,out_trade_no,total_fee,product_id);
        String res = httpsRequest(url,"POST",xml);

        Map<String,String> data = null;
        try {
            data = doXMLParse(res);
        } catch (Exception e) {
        }
        return data;
    }

    public static String NonceStr(){
        String res = Base64.encodeBase64String((Math.random()+"::"+new Date().toString()).getBytes()).substring(0, 30);
        return res;
    }

    public static String GetIp() {
        InetAddress ia=null;
        try {
            ia=InetAddress.getLocalHost();
            String localip=ia.getHostAddress();
            return localip;
        } catch (Exception e) {
            return null;
        }
    }

    public static String GetSign(Map<String,String> param,String apiKey){
        String StringA = formatUrlMap(param, false, false);
        String stringSignTemp = MD5Encode(StringA+"&key="+apiKey).toUpperCase();
        return stringSignTemp;
    }

    public static String getSignature(String data, String key) throws Exception {
        byte[] keyBytes = key.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HMAC-SHA256");
        Mac mac = Mac.getInstance("HMAC-SHA256");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : rawHmac) {
            sb.append(byteToHexString(b));
        }
        return sb.toString();
    }

    private static String byteToHexString(byte ib) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0f];
        ob[1] = Digit[ib & 0X0F];
        return new String(ob);
    }

    //Map转xml数据
    public static String GetMapToXML(Map<String,String> param){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (Map.Entry<String,String> entry : param.entrySet()) {
            sb.append("<"+ entry.getKey() +">");
            sb.append(entry.getValue());
            sb.append("</"+ entry.getKey() +">");
        }
        sb.append("</xml>");
        return sb.toString();
    }


    //微信统一下单参数设置
    public static String WXParamGenerate(String description,String out_trade_no,double total_fee,String product_id){
        int fee = (int)(total_fee * 100.00);
        Map<String,String> param = new HashMap<String,String>();
        param.put("appid", "");
        param.put("mch_id", "");
        param.put("nonce_str",NonceStr());
        param.put("body", description);
        param.put("out_trade_no",out_trade_no);
        param.put("total_fee", fee+"");
        param.put("spbill_create_ip", GetIp());
        param.put("notify_url", "http://36.33.216.230:1000/hlt-app/MyApp-1/MyApp/index.html#/home/current/paySuccess");
        param.put("trade_type", "NATIVE");
        //param.put("product_id", "");
        String api_key="";
        String sign = GetSign(param,api_key);

        param.put("sign", sign);
        return GetMapToXML(param);
    }

    //发起微信支付请求
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            System.out.println("连接超时：{}"+ ce);
        } catch (Exception e) {
            System.out.println("https请求异常：{}"+ e);
        }
        return null;
    }

    /**
     * @description 将xml字符串转换成map
     * @param xml
     * @return Map
     */
    public static Map<String, String> readStringXmlOut(String xml) {

        org.dom4j.Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (Exception e) {
        }
        org.dom4j.Element rootEle = document.getRootElement();
        List<org.dom4j.Element> elementList= rootEle.elements();
        Map<String,String> resultMap=new HashMap<>();
        for (org.dom4j.Element element:elementList) {
            resultMap.put(element.getName(),element.getStringValue());
        }
        return resultMap;
    }

    public static boolean isWechatSign(SortedMap<String, String> smap,String apiKey) {
        StringBuffer sb = new StringBuffer();
        Set es = smap.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + apiKey);
        /** 验证的签名 */
        String sign = MD5Encode(sb.toString()).toUpperCase();
        /** 微信端返回的合法签名 */
        String validSign = ((String) smap.get("sign")).toUpperCase();
        return validSign.equals(sign);
    }


//    //退款的请求方法
//    public static String httpsRequest2(String requestUrl, String requestMethod, String outputStr) throws Exception {
//        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
//        StringBuilder res = new StringBuilder("");
//        FileInputStream instream = new FileInputStream(new File("/home/apiclient_cert.p12"));
//        try {
//            keyStore.load(instream, "".toCharArray());
//        } finally {
//            instream.close();
//        }
//
//        // Trust own CA and all self-signed certs
//        SSLContext sslcontext = SSLContexts.custom()
//                .loadKeyMaterial(keyStore, "1313329201".toCharArray())
//                .build();
//        // Allow TLSv1 protocol only
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//                sslcontext,
//                new String[] { "TLSv1" },
//                null,
//                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//        CloseableHttpClient httpclient = HttpClients.custom()
//                .setSSLSocketFactory(sslsf)
//                .build();
//        try {
//
//            HttpPost httpost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
//            httpost.addHeader("Connection", "keep-alive");
//            httpost.addHeader("Accept", "*/*");
//            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//            httpost.addHeader("Host", "api.mch.weixin.qq.com");
//            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
//            httpost.addHeader("Cache-Control", "max-age=0");
//            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
//            StringEntity entity2 = new StringEntity(outputStr ,Consts.UTF_8);
//            httpost.setEntity(entity2);
//            System.out.println("executing request" + httpost.getRequestLine());
//
//            CloseableHttpResponse response = httpclient.execute(httpost);
//
//            try {
//                HttpEntity entity = response.getEntity();
//
//                System.out.println("----------------------------------------");
//                System.out.println(response.getStatusLine());
//                if (entity != null) {
//                    System.out.println("Response content length: " + entity.getContentLength());
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
//                    String text = "";
//                    res.append(text);
//                    while ((text = bufferedReader.readLine()) != null) {
//                        res.append(text);
//                        System.out.println(text);
//                    }
//
//                }
//                EntityUtils.consume(entity);
//            } finally {
//                response.close();
//            }
//        } finally {
//            httpclient.close();
//        }
//        return  res.toString();
//
//    }



    //xml解析
    public static Map<String, String> doXMLParse(String strxml) throws Exception {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if(null == strxml || "".equals(strxml)) {
            return null;
        }

        Map<String,String> m = new HashMap<String,String>();
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if(children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            m.put(k, v);
        }

        //关闭流
        in.close();
        return m;
    }

    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }
        return sb.toString();
    }

    /**
     *
     * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串
     * 实现步骤:
     * @param paraMap   要排序的Map对象
     * @param urlEncode   是否需要URLENCODE
     * @param keyToLower    是否需要将Key转换为全小写
     *            true:key转化成小写，false:不转化
     * @return
     */
    public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower)
    {
        if(paraMap == null){
            return "";
        }
        String buff = "";
        Map<String, String> tmpMap = paraMap;
        try
        {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>()
            {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2)
                {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds)
            {
                String key = item.getKey();
                String val = item.getValue();
                if (urlEncode)
                {

                    //如果是中文，则不参与编码
                    if(key.equals("package")){

                    }else if(key.equals("body")){

                    }else if(key.equals("notify_url")){

                    }else{
                        val = URLEncoder.encode(val, "utf-8");
                    }
                }
                if (keyToLower)
                {
                    buf.append(key.toLowerCase() + "=" + val);
                } else
                {
                    buf.append(key + "=" + val);
                }
                buf.append("&");
            }
            buff = buf.toString();
            if (buff.equals("") == false)
            {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e)
        {
            return null;
        }
        return buff;
    }

    /**
     * MD5编码
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }



}

