package cn.fintecher.util.ocr;

import cn.fintecher.util.http.HttpClientUtil;
import cn.fintecher.util.jiguang.JiGuangUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.Hashing;
import org.apache.commons.codec.Charsets;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月16日 11:11<br>
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

public class AliveCertification {

    private static final Logger log = LoggerFactory.getLogger(JiGuangUtil.class);
    public static String sign(List<String> values, String ticket) {
        if (values == null) {
            throw new NullPointerException("values is null");
        }
        values.removeAll(Collections.singleton(null));// remove null
        values.add(ticket);
        Collections.sort(values);

        StringBuilder sb = new StringBuilder();
        for (String s : values) {
            sb.append(s);
        }
        System.out.println(sb);
        return Hashing.sha1().hashString(sb, Charsets.UTF_8).toString().toUpperCase();

    }


    public static String getAccessToken(String url, String app_id, String secret, String grant_type, String version) throws Exception {
        url = url+"?app_id="+app_id+"&secret="+secret+"&grant_type="+grant_type+"&version="+version;
        log.info("发送活体参数getAccessToken---->" + url);
        String data = HttpClientUtil.getInstance().sendHttpGet(url);
        log.info("接收活体参数getAccessToken---->" + data);
        JSONObject jsonObject = JSON.parseObject(data);
        String access_token = jsonObject.getString("access_token");
        return access_token;
    }

    public static String getNonceTicket(String url, String app_id, String access_token, String type, String version, String user_id) throws Exception {
        url = url+"?app_id="+app_id+"&access_token="+access_token+"&type="+type+"&version="+version+"&user_id="+user_id;
        String data = HttpClientUtil.getInstance().sendHttpGet(url);
        return data;
    }

    public static String getTencentOcrSignTicket(String url, String app_id, String access_token, String type, String version) throws Exception {
        url = url+"?app_id="+app_id+"&access_token="+access_token+"&type="+type+"&version="+version;
        String data = HttpClientUtil.getInstance().sendHttpGet(url);
        return data;
    }

    public static Map getTencentOcrServiceSingStr(String appId,
                                                  String orderNo,
                                                  String singVersion,
                                                  String singTicket,
                                                  String nonceStr )throws Exception{
        Map result = new HashedMap();
        if(StringUtils.isEmpty(appId)
                || StringUtils.isEmpty(orderNo)
                || StringUtils.isEmpty(singVersion)
                || StringUtils.isEmpty(singTicket)
                || StringUtils.isEmpty(nonceStr)){
            //result.put("msg","getTencentOcrServiceSingStr接口参数异常");
            result.put("flag",false);
        }
        ArrayList<String> list = new ArrayList<String>();
        list.add(appId);
        list.add(orderNo);
        list.add(singVersion);
        list.add(singTicket);
        list.add(nonceStr);
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }
        String singStr = Hashing.sha1().hashString(sb, Charsets.UTF_8).toString().toUpperCase();
        result.put("nonceStr",nonceStr);
        result.put("singStr",singStr);
        result.put("orderNo",orderNo);
        result.put("flag",true);
        return result;
    }


    public static String getSignTicket(String url, String app_id, String access_token, String type, String version) throws Exception {
        url = url+"?app_id="+app_id+"&access_token="+access_token+"&type="+type+"&version="+version;
        log.info("活体签名发送getSignTicket---->" + url);
        String data = HttpClientUtil.getInstance().sendHttpGet(url);
        log.info("活体签名接收getSignTicket---->" + data);
        return data;
    }
    public static void main(String[] args) throws Exception {
        String app_id = "TIDAEgQO";
        String secret = "3q7deYHFnClEwrDz6xkK6NQDjfy3ERqNhbQOWjucJCUzMKLdOL2urpRA15KzRICD";
        String grant_type = "client_credential";
        String version = "1.0.0";
        String type = "SIGN";
        String url = "https://idasc.webank.com/api/oauth2/access_token";
        System.out.println(getAccessToken(url,app_id,secret,grant_type,version));
       /* String url = "https://idasc.webank.com/api/oauth2/api_ticket";
        String signtic = getSignTicket(url,app_id,"WAA0f-dGGlQHTVClazkxtmW6FX_nRhpUB01QpWs5MbZluhVzMS1AFZtxo9684dk9UiC4m68dLx1QG1MzYc0EEAVjug",type,version);
        System.out.println(signtic);*/
    }
}
