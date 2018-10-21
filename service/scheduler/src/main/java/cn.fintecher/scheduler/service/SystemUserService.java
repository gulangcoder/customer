package cn.fintecher.scheduler.service;

import cn.fintecher.model.User;

import java.util.List;

/**
 * @Description TODO
 * @Author coder_bao
 * @Date 2018/9/30 15:37
 */
public interface SystemUserService {

    /**
     * @Description 获取锁定的用户列表
     * @Param
     * @return
     * @Author coder_bao
     * @Date 2018/9/30 15:08
     */
    List<User> getLockedUserList()throws Exception;
    /**
     * @Description 解锁复合解锁条件的用户
     * @Param 
     * @return 
     * @Author coder_bao
     * @Date 2018/9/30 16:01
     */
    void unLockUser(List<String> list)throws Exception;
    
}
