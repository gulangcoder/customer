package cn.fintecher.app.model.customer;

import java.io.Serializable;
import java.util.Date;

public class EntCustProductApply implements Serializable{
    private static final long serialVersionUID = -8921588205631237092L;
    private String id;

    private String custId;

    private String productId;

    //产品申请状态(0未申请,1申请中，2授信中，3申请成功，4申请失败)
    private Short applyStatus;

    private String companyCode;

    private Date createTime;

    private Date updateTime;

    //步骤 1身份认证 2个人信息 3联系人 4影像资料 5人脸识别 6提交
    private Short status;

    //是否有影像资料 1有 0无
    private Integer haveVideo;

    public Integer getHaveVideo() {
        return haveVideo;
    }

    public void setHaveVideo(Integer haveVideo) {
        this.haveVideo = haveVideo;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public Short getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Short applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}