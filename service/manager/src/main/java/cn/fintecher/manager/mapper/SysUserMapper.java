package cn.fintecher.manager.mapper;


import cn.fintecher.manager.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysUserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    //获取用户列表
    List<SysUser> getUserList(Map map);

    //添加查询用户是否存在
    SysUser getUserByAccount(@Param("account") String account);

    //修改查询用户是否存在
    String getUserByUpdate(SysUser sysUser);

    //查询用户角色
    String getRoleIdByUserId(@Param("userId") String userId);

    String getOldRoleIdByUserId(@Param("userId") String userId);
}