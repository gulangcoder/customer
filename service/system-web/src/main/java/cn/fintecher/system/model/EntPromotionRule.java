package cn.fintecher.system.model;
import java.io.Serializable;


/**
 * 
 * 活动配置规则表
 * 
 **/
@SuppressWarnings("serial")
public class EntPromotionRule implements Serializable {

	/**主键**/
	private String id;

	/**条件**/
	private String condit;

	/**结果**/
	private String result;

	/**所属活动**/
	private String promotionCode;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setCondit(String condit){
		this.condit = condit;
	}

	public String getCondit(){
		return this.condit;
	}

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return this.result;
	}

	public void setPromotionCode(String promotionCode){
		this.promotionCode = promotionCode;
	}

	public String getPromotionCode(){
		return this.promotionCode;
	}

}
