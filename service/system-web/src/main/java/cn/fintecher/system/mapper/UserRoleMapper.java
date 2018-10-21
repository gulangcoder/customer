package cn.fintecher.system.mapper;


import cn.fintecher.system.model.UserRole;

/**
 * Title :
 * Description : 用户-角色管理表
 * Create on : 2018年05月21日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username: wangtao
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
public interface UserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 09:56
     * @Params:
     * @Description: 添加角色之前，把之前的此用户的角色停用
     * @return:
     */
    int deleteByUserId(String userId);

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