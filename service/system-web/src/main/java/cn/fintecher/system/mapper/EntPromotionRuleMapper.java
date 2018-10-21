package cn.fintecher.system.mapper;

import cn.fintecher.system.model.EntPromotionRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * EntPromotionRuleMapper数据库操作接口类
 * 
 **/
@Mapper
public interface EntPromotionRuleMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	EntPromotionRule selectByPrimaryKey(@Param("id") Long id);

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(@Param("id") Long id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(EntPromotionRule record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(EntPromotionRule record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(EntPromotionRule record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(EntPromotionRule record);

	//批量插入数据
	int insertList(List<EntPromotionRule> list);
	//根据活动的id删除对应的规则
	int deleteByPromotionCode(String promotionCode);
	//根据活动的id查询对应的规则
	List<EntPromotionRule> selectByPromotionCode(String promotionCode);
}