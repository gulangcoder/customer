package cn.fintecher.system.mapper;

import cn.fintecher.system.model.EntMessageTemplet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntMessageTempletMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntMessageTemplet record);

    int insertSelective(EntMessageTemplet record);

    EntMessageTemplet selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntMessageTemplet record);


    int updateByPrimaryKey(EntMessageTemplet record);

    /**
     * 查询消息模板列表
     * @param map
     * @return
     */
    List<Map> findList(Map map);

    EntMessageTemplet getMsgTemplDetailById(Map map);

    List<Map> getTempDetailList(Map param);
}