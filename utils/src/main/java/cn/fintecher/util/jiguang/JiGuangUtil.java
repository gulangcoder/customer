package cn.fintecher.util.jiguang;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.LoggerFactory;


/**
 * @Author: wangtao
 * @Date: 2018/05/15 09:56
 * @Description: 极光推送工具类
 */
public class JiGuangUtil {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(JiGuangUtil.class);
    //在极光注册上传应用的 appKey 和 masterSecret
    private static String appKey = "ae915ff2d5449b6f76cf9a65";
    //必填，每个应用都对应一个masterSecret
    private static String masterSecret = "589c4c394e889ada5d709026";
    /**
     * @Author: wangtao
     * @Date: 2018/05/15 10:34
     * @Params: title   推送的标题
     * @Params: message 推送的内容
     * @Description: 根据别名向单个用户发送推送
     * @return: boolean
     */
    public static boolean alias(String title, String message, String phone) throws APIConnectionException, APIRequestException {
        PushPayload payload = push_ios_android_All(title,message,phone);
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
        try {
            PushResult result = jpushClient.sendPush(payload);
           /* if(result.statusCode == ErrorCodeEnum.NOERROR.value()){*/
                return true;
          /*  }else {
                return false;
            }*/
        }catch (APIConnectionException e) {
            log.error("链接异常", e);
            return false;
        }catch (APIRequestException e) {
            log.error("请求异常", e);
            log.info("请求状态: " + e.getStatus());
            log.info("失败CODE: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            log.info("Msg ID: " + e.getMsgId());
            return false;
        }
    }
    //ios&&安卓发送推送消息(v3版本java SDK)
    public static PushPayload push_ios_android_All(String title, String message, String phone) {
        return PushPayload.newBuilder().setPlatform(Platform.all()) .setAudience(Audience.alias(phone))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title)
                                .setAlert(message)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(message)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(false)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(3600)//消息在JPush服务器的失效时间（测试使用参数）
                        .build()).build();
    }
}
