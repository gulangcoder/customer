package cn.fintecher.common.contract;

import java.util.Date;

public class EntContractBookDetail {
    private String id;

    private String contractBookNo;

    private String contractTemplId;

    private String contractTitle;

    private String contractType;

    private String customerId;

    private String customerName;

    private String customerCardNo;

    private Date createTime;

    private String companyCode;

    private String productId;

    private String content;
    //合同签订状态(0签约中，1签约成功，2签约失败)
    private Short status;
    //订单编号
    private String orderId;

    private Date contractStartDate;

    private Date contractEndDate;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(Date contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public Date getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }


    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContractBookNo() {
        return contractBookNo;
    }

    public void setContractBookNo(String contractBookNo) {
        this.contractBookNo = contractBookNo == null ? null : contractBookNo.trim();
    }

    public String getContractTemplId() {
        return contractTemplId;
    }

    public void setContractTemplId(String contractTemplId) {
        this.contractTemplId = contractTemplId == null ? null : contractTemplId.trim();
    }

    public String getContractTitle() {
        return contractTitle;
    }

    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle == null ? null : contractTitle.trim();
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType == null ? null : contractType.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerCardNo() {
        return customerCardNo;
    }

    public void setCustomerCardNo(String customerCardNo) {
        this.customerCardNo = customerCardNo == null ? null : customerCardNo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}