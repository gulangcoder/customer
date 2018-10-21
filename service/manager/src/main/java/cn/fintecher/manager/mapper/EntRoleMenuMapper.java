package cn.fintecher.manager.mapper;


import cn.fintecher.manager.model.EntRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface EntRoleMenuMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntRoleMenu record);

    int insertSelective(EntRoleMenu record);

    EntRoleMenu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntRoleMenu record);

    int updateByPrimaryKey(EntRoleMenu record);

    int addEntRoleMenuList(List<EntRoleMenu> list);

    int deleteEntRoleMenu(String roleId);

    int delEntMenuNotInModelDetail(Map map);
}