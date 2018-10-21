package cn.fintecher.manager.service;/**
 * Created by Administrator on 2018/6/12 0012.
 */

import cn.fintecher.manager.model.EntRole;

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
public interface SysRoleService {
    List<EntRole> getRoleList(Map map);

    /**
     * @Author: wangtao
     * @Date: 2018/05/23 13:28
     * @Params: role
     *              角色实体
     * @Description: 添加用户
     * @return: map
     */
    Map addRole(EntRole role) throws Exception;

    /**
     * @Author: wangtao
     * @Date: 2018/05/23 13:29
     * @Params: roleId
     *              角色id
     * @Description: 查询单个角色
     * @return:
     */
    EntRole getRoleById(String roleId);

    /**
     * @Author: wangtao
     * @Date: 2018/05/23 13:29
     * @Params: role
     *              角色实体
     * @Description: 修改角色
     * @return: Map
     */
    Map updateRole(EntRole role);

    /**
     * @Author: wangtao
     * @Date: 2018/05/23 13:30
     * @Params: role
     *             角色实体
     * @Description: 启用，停用
     * @return:
     */
    Map updateState(EntRole role);


    List<Map> getAllRole(String companyCode);

}
