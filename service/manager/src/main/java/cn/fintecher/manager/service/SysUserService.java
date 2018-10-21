package cn.fintecher.manager.service;

import cn.fintecher.manager.model.SysUser;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description :
 * Create on : 2018年06月12日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username: wangtao
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
public interface SysUserService {
    //获取用户列表
    List<SysUser> getUserList(Map map);

    //添加用户
    Map addUser(SysUser user) throws Exception;

    //修改用户
    Map updateUser(SysUser user);

    //修改回显
    SysUser getUserByUserId(String userId);

    //修改状态
    Map updateState(SysUser user);

    //重置密码
    Map resetPassword(String userId, String updateUser) throws Exception;

    //分配角色先获取角色
    Map getRole(String userId);

    String getOldRoleIdByUserId(String userId);
}
