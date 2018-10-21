package cn.fintecher.system.model.requestModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "授信列表传入参数")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditRequest extends CommonPageInfo {
    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "身份证号")
    private String idcardNum;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "申请授信开始时间")
    private String startTime;

    @ApiModelProperty(value = "申请授信结束时间")
    private String endTime;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdcardNum() {
        return idcardNum;
    }

    public void setIdcardNum(String idcardNum) {
        this.idcardNum = idcardNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
