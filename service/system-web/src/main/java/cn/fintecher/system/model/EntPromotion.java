package cn.fintecher.system.model;
import java.io.Serializable;


/**
 * 
 * 活动配置表
 * 
 **/
@SuppressWarnings("serial")
public class EntPromotion implements Serializable {

	/**主键**/
	private String id;

	/**活动名称**/
	private String promotionName;

	/**创建人**/
	private String createUser;

	/**创建时间**/
	private java.util.Date createTime;

	/**修改人**/
	private String updateUser;

	/**修改时间**/
	private java.util.Date updateTime;

	/**备注**/
	private String remark;

	/**状态**/
	private Integer status;

	/**企业编号**/
	private String companyCode;

	/**活动开始时间**/
	private java.util.Date startTime;

	/**活动结束时间**/
	private java.util.Date endTime;

	/**promotion_type:活动类型 discount：满折 reduce：满减 gift：满赠**/
	private String promotionType;

	/**promotion_pattern:活动方式
            num：数量
            money：金额**/
	private String promotionPattern;

	/**是否订制（1：是，0：否）**/
	private Integer reservation;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setPromotionName(String promotionName){
		this.promotionName = promotionName;
	}

	public String getPromotionName(){
		return this.promotionName;
	}

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return this.createUser;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}

	public String getUpdateUser(){
		return this.updateUser;
	}

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setCompanyCode(String companyCode){
		this.companyCode = companyCode;
	}

	public String getCompanyCode(){
		return this.companyCode;
	}

	public void setStartTime(java.util.Date startTime){
		this.startTime = startTime;
	}

	public java.util.Date getStartTime(){
		return this.startTime;
	}

	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}

	public java.util.Date getEndTime(){
		return this.endTime;
	}

	public void setPromotionType(String promotionType){
		this.promotionType = promotionType;
	}

	public String getPromotionType(){
		return this.promotionType;
	}

	public void setPromotionPattern(String promotionPattern){
		this.promotionPattern = promotionPattern;
	}

	public String getPromotionPattern(){
		return this.promotionPattern;
	}

	public void setReservation(Integer reservation){
		this.reservation = reservation;
	}

	public Integer getReservation(){
		return this.reservation;
	}

}
