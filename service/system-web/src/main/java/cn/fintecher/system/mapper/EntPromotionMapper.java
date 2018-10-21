package cn.fintecher.system.mapper;

import cn.fintecher.system.model.EntPromotion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * EntPromotionMapper数据库操作接口类
 * 
 **/
@Mapper
public interface EntPromotionMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	EntPromotion selectByPrimaryKey(@Param("id") String id);

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(@Param("id") String id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(EntPromotion record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(EntPromotion record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(EntPromotion record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(EntPromotion record);

	//查询列表
    List<EntPromotion> getEntPromotionList(Map map);
	//根据Id删除活动
	void deleteById(@Param("id") String id);
	//根据Id查询活动
	EntPromotion selectById(@Param("id") String id);

	//查询有效的活动
	List<EntPromotion> getEffectiveActivity(@Param("companyCode") String companyCode);
}