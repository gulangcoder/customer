package cn.fintecher.common.language;

import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.util.redis.RedisKeyConstants;
import cn.fintecher.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import java.util.Locale;


/**
 * @ClassName LocaleMessage
 * @Description 获取当前语言版本的资源
 * @Author coder_bao
 * @Date 2018/8/30 15:29
 */
@Component
public class LocaleMessage {

    private static MessageSource messageSource;
    @Autowired
    public void setMessageSource(MessageSource messageSource){
        this.messageSource=messageSource;
    }

    public static String get(String code) {
        Locale locale = getLocale();
        return messageSource.getMessage(code, null, locale);
    }

    public static Locale getDefaultLocale() {
        Locale locale = RedisUtil.get(RedisKeyConstants.SYS_LANGUAGE);
        if (locale==null){
            locale = LocaleContextHolder.getLocale();
            RedisUtil.set(RedisKeyConstants.SYS_LANGUAGE,locale);
        }
        return locale;
    }

    /**
     * 获取当前用户的语言
     * @return
     */
    public static Locale getLocale() {
        UserInfo userInfo = UserContextUtil.getUserInfo();
        if (userInfo != null) {
            Locale locale = RedisUtil.get(RedisKeyConstants.SYS_LANGUAGE+UserContextUtil.getUserTokenId());
            if (locale==null){
                return getDefaultLocale();
            }else{
                return locale;
            }
        } else {
            return getDefaultLocale();
        }
    }

    public static void setLocale(String tokenId){
        if (tokenId!=null&&!"".equals(tokenId)){
            Locale locale = getDefaultLocale();
            RedisUtil.set(RedisKeyConstants.SYS_LANGUAGE+tokenId,locale);

        }
    }
    public void changeRedisLocale(Locale locale){
        RedisUtil.set(RedisKeyConstants.SYS_LANGUAGE,locale);
        UserInfo userInfo = UserContextUtil.getUserInfo();
        if (userInfo != null) {
            setLocale(UserContextUtil.getUserTokenId());
        }
        LocaleContextHolder.setDefaultLocale(locale);
    }
}
