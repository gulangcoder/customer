package cn.fintecher.system.model.requestModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "借款列表传入参数")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BorrowRequest extends CommonPageInfo{

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "状态:1借款申请;2放款审批;3放款中;4已放款;5结清;6拒绝;7放款失败")
    private Integer state;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "身份证号")
    private String idcardNum;


    @ApiModelProperty(value = "产品名称")
    private String productName;


    @ApiModelProperty(value = "还款方式: 0等额本息 1等额本金 2先息后本 3到期一次还本付息 4等本等息")
    private String paymentWay;

    @ApiModelProperty(value = "期数")
    private Integer periods;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

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

    public String getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }
}
