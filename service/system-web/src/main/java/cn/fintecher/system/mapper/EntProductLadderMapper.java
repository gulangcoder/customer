package cn.fintecher.system.mapper;

import cn.fintecher.system.model.EntProductLadder;

import java.util.List;
import java.util.Map;

public interface EntProductLadderMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntProductLadder record);

    int insertSelective(EntProductLadder record);

    EntProductLadder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntProductLadder record);

    int updateByPrimaryKey(EntProductLadder record);

    int updateProductLadder(Map productLadder);

    List<EntProductLadder> selectLadderByMap(Map map);
}