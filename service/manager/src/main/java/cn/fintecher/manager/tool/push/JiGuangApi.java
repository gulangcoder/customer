package cn.fintecher.manager.tool.push;

import cn.fintecher.util.jiguang.JiGuangUtil;
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
 * Title :
 * Description : @极光推送Api@
 * Create on : 2018年09月05日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */

public class JiGuangApi {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(JiGuangUtil.class);
    /**
     * @Author: wangtao
     * @Date: 2018/05/15 10:34
     * @Params: title   推送的标题
     * @Params: message 推送的内容
     * @Description: 根据别名向单个用户发送推送
     * @return: boolean
     */
    public static  boolean alias(String title, String message, String phone,String masterSecret,String appKey) throws APIConnectionException, APIRequestException {
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
    public  static PushPayload push_ios_android_All(String title, String message, String phone) {
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