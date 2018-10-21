package cn.fintecher.system.service.impl;


import cn.fintecher.system.mapper.UserRoleMapper;
import cn.fintecher.system.model.UserRole;
import cn.fintecher.system.service.UserRoleService;
import cn.fintecher.util.CreateIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * Title :
 * Description :
 * Create on : 2018年05月24日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username: wangtao
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;
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
    @Override
    public void addUserRole(String userId, String createUser, String[] roleIds) throws Exception{
        //添加之前先把之前的角色删除
        userRoleMapper.deleteByUserId(userId);

        UserRole userRole = new UserRole();
        userRole.setCompanyCode("");
        userRole.setOrgId("");
        userRole.setUserId(userId);
        userRole.setCreateUser(createUser);
        userRole.setCreateTime(new Date());
        if(!ObjectUtils.isEmpty(roleIds)){
            for(String roleId : roleIds){
                userRole.setId(CreateIDUtil.getId());
                userRole.setRoleId(roleId);
                userRoleMapper.insertSelective(userRole);
            }
        }
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 10:32
     * @Params: userId
     *              用户id
     * @Description: 获取用户的角色id
     * @return: List
     */
    @Override
    public String getRoleByUserId(String userId) {
        String roleId = userRoleMapper.getRoleByUserId(userId);
        return roleId;
    }
}
