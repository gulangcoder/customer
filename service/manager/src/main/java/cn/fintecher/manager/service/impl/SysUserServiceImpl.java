package cn.fintecher.manager.service.impl;

import cn.fintecher.manager.mapper.EntRoleMapper;
import cn.fintecher.manager.mapper.SysUserMapper;
import cn.fintecher.manager.model.EntRole;
import cn.fintecher.manager.model.SysUser;
import cn.fintecher.manager.service.SysUserService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.MD5Util;
import cn.fintecher.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
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
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private EntRoleMapper entRoleMapper;

    @Override
    public List<SysUser> getUserList(Map map) {
        String account = String.valueOf(map.get("account"));
        if(StringUtil.isNotEmpty(account)){
            account = StringUtil.escapeSQLSpecial(account);
            map.put("account",account);
        }
        String realName = String.valueOf(map.get("realName"));
        if(StringUtil.isNotEmpty(realName)){
            realName = StringUtil.escapeSQLSpecial(realName);
            map.put("realName",realName);
        }
        String tel = String.valueOf(map.get("tel"));
        if(StringUtil.isNotEmpty(tel)){
            tel = StringUtil.escapeSQLSpecial(tel);
            map.put("tel",tel);
        }
        List<SysUser> list = sysUserMapper.getUserList(map);
        return list;
    }

    @Override
    public Map addUser(SysUser user) throws Exception {
        Map map = new HashMap();
        String account = user.getAccount();
        //查询账户是否已存在
        SysUser model = sysUserMapper.getUserByAccount(account);
        if(model!=null){
            map.put("flag",false);
            map.put("msg","该用户已存在");
            return map;
        }
        String userId = CreateIDUtil.getId();
        user.setUserId(userId);
        //密码加密
        user.setPassword(MD5Util.GetMD5Code(user.getPassword()));
        //company_code企业编号,org_id组织编号,employee_id员工编号
        user.setErrorCount(0);
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        int n = sysUserMapper.insertSelective(user);
        if(n>0){
            map.put("flag",true);
            map.put("msg","添加成功！");
        }else{
            map.put("flag",false);
            map.put("msg","添加失败！");
        }
        return map;
    }

    @Override
    public Map updateUser(SysUser user) {
        Date date = new Date();
        user.setUpdateTime(date);
        Map map = new HashMap();
        String id = sysUserMapper.getUserByUpdate(user);
        if(StringUtil.isNotEmpty(id)){
            map.put("flag",false);
            map.put("msg","账号已存在！");
            return map;
        }
        int n = sysUserMapper.updateByPrimaryKeySelective(user);
        if(n>0){
            map.put("flag",true);
            map.put("msg","修改成功！");
        }else{
            map.put("flag",false);
            map.put("msg","修改失败！");
        }
        return map;
    }

    @Override
    public SysUser getUserByUserId(String userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public Map updateState(SysUser user) {
        user.setUpdateTime(new Date());
        Short status = user.getStatus();
        if(status == 1){
            user.setErrorCount(0);
        }
        int n = sysUserMapper.updateByPrimaryKeySelective(user);
        Map map = new HashMap();
        if(n>0){
            map.put("flag",true);
            map.put("msg","操作成功！");
        }else{
            map.put("flag",false);
            map.put("msg","操作失败！");
        }
        return map;
    }

    @Override
    public Map resetPassword(String userId, String updateUser) throws Exception {
        Map map = new HashMap();
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setUpdateUser(updateUser);
        //初始密码进行加密
        String password = MD5Util.GetMD5Code("123456");
        user.setPassword(password);
        user.setUpdateTime(new Date());
        int n = sysUserMapper.updateByPrimaryKeySelective(user);
        if(n>0){
            map.put("flag",true);
            map.put("msg","操作成功！");
        }else{
            map.put("flag",false);
            map.put("msg","操作失败！");
        }
        return map;
    }

    /**
     * @Author: wangtao
     * @Date: 2018/06/12 17:31
     * @Params:
     * @Description: 分配角色先获取角色
     * @return:
     */
    @Override
    public Map getRole(String userId) {
        //获取所有平台有效的角色
        List<EntRole> roleAllList = entRoleMapper.getRoleList();
        //获取用户自己本身的角色
        String userRoleId = sysUserMapper.getRoleIdByUserId(userId);
        Map map = new HashMap();
        map.put("roleAllList",roleAllList);
        map.put("userRoleId",userRoleId);
        return map;
    }
    /**
     * @Author: wangtao
     * @Date: 2018/05/24 09:30
     * @Params: companyCode：公司code
     * @Description: 角色分配，获取角色
     * @return:
     */
    @Override
    public String getOldRoleIdByUserId(String userId) {
        String roleId = sysUserMapper.getOldRoleIdByUserId(userId);
        return roleId;
    }
}
