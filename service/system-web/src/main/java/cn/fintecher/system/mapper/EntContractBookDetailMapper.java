package cn.fintecher.system.mapper;

import cn.fintecher.common.contract.EntContractBookDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntContractBookDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntContractBookDetail record);;

    int insertSelective(EntContractBookDetail record);

    EntContractBookDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntContractBookDetail record);


    int updateByPrimaryKey(EntContractBookDetail record);

    /**
     * 查询数据列表
     * @param map
     * @return
     */
    List<Map>findList(Map map);


    List<String> getListByIds(List<String>list);

    List<Map> selectListByParams(EntContractBookDetail entContractBookDetail);
}