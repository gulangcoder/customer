package cn.fintecher.app.model.customer;

import cn.fintecher.common.userinfo.UserInfo;

import java.io.Serializable;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/18
 * @Description:
 */
public class CustomerResponse implements Serializable{

    private static final long serialVersionUID = 2482488341373344068L;

    String token;
    UserInfo userInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
