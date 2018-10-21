package cn.fintecher.util;

import com.alibaba.fastjson.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @Author: wangtao
 * @Date: 2018/05/14 15:56
 * @Description: 签名工具类
 */
public class SignatureRemindUtil {

    // 随机签名字符串
    private final static String ECHOSTR = "zzl_apt";

    /**
     * @Author:wangtao
     * @Date:2018/05/14 16:02
     * @Params: accountId:申请id ，time：时间戳(yyyyMMddhhmmss)
     * @Description:获取签名
     * @return:返回加密后的签名
     */
    public static String getSignature(String accountId, String time) {
        String str = ECHOSTR + ":" + accountId + ":" + time;
        return new String(new SHA1().getDigestOfString(str.getBytes()));
    }

    /**
     * @Author:wangtao
     * @Date:2018/05/14 16:05
     * @Params: accountId:申请id ，time：时间戳(yyyyMMddhhmmss) ， signature：已生成的签名
     * @Description: 验证getSignature方法的签名
     * @return: true/false 是否验证成功
     */
    public static boolean checkSignature(String accountId, String time, String signature) {
        String str = ECHOSTR + ":" + accountId + ":" + time;
        String encryStr = new String(new SHA1().getDigestOfString(str.getBytes()));
        if (signature.equals(encryStr)){
            return true;
        }
        return false;
    }

    /**
     * @Author:wangtao
     * @Date:2018/05/14 16:03
     * @Params:
     * @Description:根据随机生成的申请id生成签名
     * @return:返回申请id（accountId）、时间戳（timestamp）和加密的签名（sign）组成的json串
     */
    public static String createSign() throws Exception {
        JSONObject json = new JSONObject();
        String timestamp = DateConversionUtil.getCurrentTime();
        String accountId = RandomUtil.randomForNumAndChar(20);
        json.put("timestamp", timestamp);
        json.put("accountId", accountId);
        json.put("sign", getSHA1(ECHOSTR, timestamp, accountId));
        return json.toString();
    }

    /**
     * @Author:wangtao
     * @Date:2018/05/14 16:07
     * @Params: signStr：由申请id（accountId）、时间戳（timestamp）、签名（sign）组成的json串
     * @Description: 验证createSign方法的签名
     * @return: true/false 是否验证成功
     */
    public static boolean checkSignature(String signStr) {
        try {
            JSONObject json = JSONObject.parseObject(signStr);
            String timestamp = json.getString("timestamp");
            String accountId = json.getString("accountId");
            String sign = json.getString("sign");
            String shaSignStr = getSHA1(ECHOSTR, timestamp, accountId);
            return sign.equals(shaSignStr);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getSHA1(String token, String timestamp, String accountId) throws NoSuchAlgorithmException {
        String[] array = new String[] { token, timestamp, accountId };
        StringBuffer sb = new StringBuffer();
        // 字符串排序
        Arrays.sort(array);
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
        }
        String str = sb.toString();
        // SHA1签名生成
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(str.getBytes());
        byte[] digest = md.digest();

        StringBuffer hexstr = new StringBuffer();
        String shaHex = "";
        for (int i = 0; i < digest.length; i++) {
            shaHex = Integer.toHexString(digest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexstr.append(0);
            }
            hexstr.append(shaHex);
        }
        return hexstr.toString();
    }
}
