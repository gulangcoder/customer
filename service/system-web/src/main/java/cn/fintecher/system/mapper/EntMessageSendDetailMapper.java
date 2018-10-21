package cn.fintecher.system.mapper;

import cn.fintecher.common.sms.EntMessageSendDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntMessageSendDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntMessageSendDetail record);

    int insertSelective(EntMessageSendDetail record);

    EntMessageSendDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntMessageSendDetail record);

    int updateByPrimaryKeyWithBLOBs(EntMessageSendDetail record);

    int updateByPrimaryKey(EntMessageSendDetail record);


    /**
     * 查询列表
     * @param map
     * @return
     */
    List<Map> findList(Map map);

    List<EntMessageSendDetail> getMsgTemplListOfApp(Map param);
}