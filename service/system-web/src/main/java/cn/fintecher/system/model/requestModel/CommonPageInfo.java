package cn.fintecher.system.model.requestModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


@ApiModel(value = "分页传入参数")
@JsonIgnoreProperties
public class CommonPageInfo implements Serializable{
	
	private static final long serialVersionUID = 3607626990775704759L;

	@ApiModelProperty(value="当前页")
	private Integer pageIndex;
	
	@ApiModelProperty(value="每页多少条")
	private Integer pageSize;

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
