package cn.fintecher.manager.service.impl;/**
 * Created by Administrator on 2018/6/12 0012.
 */

import cn.fintecher.manager.mapper.EntRoleMapper;
import cn.fintecher.manager.model.EntRole;
import cn.fintecher.manager.service.SysRoleService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年06月12日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:zhangtao
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private EntRoleMapper roleMapper;

    @Override
    public List<EntRole> getRoleList(Map map) {
        EntRole role = new EntRole();
        String roleName = String.valueOf(map.get("roleName"));
        if (StringUtil.isNotEmpty(roleName)) {
            roleName = StringUtil.escapeSQLSpecial(roleName);
            role.setRoleName(roleName);
        }
        role.setCompanyCode(String.valueOf(map.get("companyCode")));
        List<EntRole> list = roleMapper.getRoleAllList(role);
        return list;
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/23 13:28
     * @Params: role
     * 角色实体
     * @Description: 添加用户
     * @return: map
     */
    @Override
    public Map addRole(EntRole role) throws Exception {
        Map map = new HashMap();
        String roleName = role.getRoleName();
        //查询账户是否已存在
        EntRole model = roleMapper.getRoleByRoleName(roleName, role.getCompanyCode());
        if (model != null) {
            map.put("flag", false);
            map.put("msg", "角色名称已存在");
            return map;
        }
        String roleId = CreateIDUtil.getId();
        role.setRoleId(roleId);
        //company_code企业编号,org_id组织编号
        Date date = new Date();
        role.setCreateTime(date);
        role.setUpdateTime(date);
        roleMapper.insertSelective(role);
        map.put("flag", true);
        map.put("msg", "添加成功！");
        return map;
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/23 13:29
     * @Params: roleId
     * 角色id
     * @Description: 查询单个角色
     * @return: EntRole
     */
    @Override
    public EntRole getRoleById(String roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/23 13:29
     * @Params: role
     * 角色实体
     * @Description: 修改角色
     * @return: Map
     */
    @Override
    public Map updateRole(EntRole role) {
        Date date = new Date();
        role.setUpdateTime(date);
        List list = roleMapper.getRoleByUpdate(role);
        Map map = new HashMap();
        if (!ObjectUtils.isEmpty(list)) {
            map.put("flag", false);
            map.put("msg", "角色已存在！");
            return map;
        }
        int n = roleMapper.updateByPrimaryKeySelective(role);
        if (n > 0) {
            map.put("flag", true);
            map.put("msg", "修改成功！");
        } else {
            map.put("flag", false);
            map.put("msg", "修改失败！");
        }
        return map;
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/23 13:30
     * @Params: role
     * 角色实体
     * @Description: 启用，停用
     * @return:
     */
    @Override
    public Map updateState(EntRole role) {
        role.setUpdateTime(new Date());
        int n = roleMapper.updateByPrimaryKeySelective(role);
        Map map = new HashMap();
        if (n > 0) {
            map.put("flag", true);
            map.put("msg", "操作成功！");
        } else {
            map.put("flag", false);
            map.put("msg", "操作失败！");
        }
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
    public List<Map> getAllRole(String companyCode) {
        List<Map> list = roleMapper.getAllRole(companyCode);
        return list;
    }

}
