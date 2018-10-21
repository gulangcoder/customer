package cn.fintecher.system.mapper;

import cn.fintecher.system.model.EntProductDetailCompany;
import cn.fintecher.system.model.EntProductVideo;

import java.util.List;
import java.util.Map;

public interface EntProductVideoMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntProductVideo record);

    int insertSelective(EntProductVideo record);

    EntProductVideo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntProductVideo record);

    int updateByPrimaryKey(EntProductVideo record);

    List<EntProductVideo> selectVideoByMap(Map map);

    int updateProductVideo(EntProductVideo productVideo);
}