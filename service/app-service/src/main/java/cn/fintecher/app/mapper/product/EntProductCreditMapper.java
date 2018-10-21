package cn.fintecher.app.mapper.product;


import cn.fintecher.app.model.product.EntProductCredit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EntProductCreditMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntProductCredit record);

    int insertSelective(EntProductCredit record);

    EntProductCredit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntProductCredit record);

    int updateByPrimaryKey(EntProductCredit record);

    /***
     * 获取信率集合
     * @param record
     * @return
     */
    List<EntProductCredit> getEntProductCreditList(EntProductCredit record);
}