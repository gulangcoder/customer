package cn.fintecher.common.logger;

import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName LoggerCommon
 * @Description TODO
 * @Author coder_bao
 * @Date 2018/8/30 10:15
 */
public class LoggerCommon {
    private static Logger logger ;

    public static String trace(String message){
        String msg = "";
        UserInfo userInfo = UserContextUtil.getUserInfo();
        if (userInfo!=null){
            msg = "["+UserContextUtil.getUserTokenId()+"] ["+userInfo.getAccount()+"]:";
        }else{
            msg = "[null] [null]:";
        }
        return msg + message;
    }

    public static void info(Class<?> clazz, String message){
        if (logger==null){
            logger=LoggerFactory.getLogger(clazz);
        }
        logger.info(trace(message));
    }

}
