package cn.fintecher.app.mapper.order;


import cn.fintecher.app.model.order.EntCustQuota;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntCustQuotaMapper {

    int deleteByPrimaryKey(String id);

    int insert(EntCustQuota record);

    int insertSelective(EntCustQuota record);

    EntCustQuota selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntCustQuota record);

    int updateByPrimaryKey(EntCustQuota record);

    List<EntCustQuota> getList(Map<String,Object> map);

    /***
     * 获取用户最新额产品额度信息
     * @param map
     * @return EntCustQuota
     */
    EntCustQuota getCustQuota(Map<String,Object> map);

    //授信列表 后管
    List<Map> getQuotaListByMap(Map map);
}