package cn.fintecher.manager.model;


import cn.fintecher.common.userinfo.UserInfo;
import lombok.Data;

import java.util.Map;

/**
 * Created by ChenChang on 2017/3/7.
 */
@Data
public class UserLoginResponse {
    String token;
    boolean reset; //true是需要修改密码
    String regDay;
    UserInfo userInfo;

    Map<String,Object> data;
}
