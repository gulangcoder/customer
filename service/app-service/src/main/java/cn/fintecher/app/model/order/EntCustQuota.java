package cn.fintecher.app.model.order;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Title :
 * Description : @额度model@
 * Create on : 2018年09月15日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 */
public class EntCustQuota {

    private String id;

    //用户id
    private String custId;

    // TODO: 2018/9/15  custModel

    //初始额度
    private BigDecimal initialAmount;

    //总额
    private BigDecimal totalAmount;

    //可用额度
    private BigDecimal availableAmount;

    //已用额度
    private BigDecimal usedAmount;

    //产品系列id
    private String productId;


    // TODO: 2018/9/15  productModel

    //额度状态：0提交审核中，1正常，2产品下架，3额度失效，4拒绝
    private Integer state;

    //授信发起时间
    private Date creditSendTime;

    //授信返回时间
    private Date creditReturnTime;

    //授信结果（1通过，2拒绝）
    private Integer creditResult;

    //拒绝code
    private String refuseCode;

    //有效期
    private Date effectiveTime;

    //启用时间
    private Date enableTime;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //产品详情id
    private String productDetailId;

    //当日拒绝次数
    private Integer refuseCount;

    //绑卡状态 0未绑卡 1已绑卡
    private Integer bindBank;

    //当前借款状态 0无欠款 1还款中 2已逾期
    private int loanState;

    public Integer getLoanState() {
        return loanState;
    }

    public void setLoanState(Integer loanState) {
        this.loanState = loanState;
    }

    public Integer getBindBank() {
        return bindBank;
    }

    public void setBindBank(Integer bindBank) {
        this.bindBank = bindBank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreditSendTime() {
        return creditSendTime;
    }

    public void setCreditSendTime(Date creditSendTime) {
        this.creditSendTime = creditSendTime;
    }

    public Date getCreditReturnTime() {
        return creditReturnTime;
    }

    public void setCreditReturnTime(Date creditReturnTime) {
        this.creditReturnTime = creditReturnTime;
    }

    public Integer getCreditResult() {
        return creditResult;
    }

    public void setCreditResult(Integer creditResult) {
        this.creditResult = creditResult;
    }

    public String getRefuseCode() {
        return refuseCode;
    }

    public void setRefuseCode(String refuseCode) {
        this.refuseCode = refuseCode == null ? null : refuseCode.trim();
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(Date enableTime) {
        this.enableTime = enableTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(String productDetailId) {
        this.productDetailId = productDetailId;
    }

    public Integer getRefuseCount() {
        return refuseCount;
    }

    public void setRefuseCount(Integer refuseCount) {
        this.refuseCount = refuseCount;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(BigDecimal usedAmount) {
        this.usedAmount = usedAmount;
    }
}