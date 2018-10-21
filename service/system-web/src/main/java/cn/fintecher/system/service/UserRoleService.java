package cn.fintecher.system.service;

/**
 * Title :
 * Description : 用户角色管理
 * Create on : 2018年05月24日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username: wangtao
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
public interface UserRoleService {
    /**
     * @Author: wangtao
     * @Date: 2018/05/24 09:51
     * @Params: userId
     *              用户id
     *  @Params: roleIds
     *              角色id数组
     * @Description: 用户添加角色
     * @return: Map
     */
    void addUserRole(String userId, String createUser, String[] roleIds)throws Exception;

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 10:32
     * @Params: userId
     *              用户id
     * @Description: 获取用户的角色id
     * @return: List
     */
    String getRoleByUserId(String userId);
}
