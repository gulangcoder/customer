package cn.fintecher.app.model.customer;

import java.io.Serializable;
import java.util.Date;

public class EntCustomerRelationship implements Serializable{
    private static final long serialVersionUID = 8948719918806921952L;
    private String id;

    //客户表详情表id
    private String custId;

    //企业编码
    private String companyCode;

    //联系人名称
    private String connName;

    //联系人电话
    private String connTel;

    //与联系人关系 (数据字典itemCode=connType)
    private String connType;

    private String connTypeHH;

    //其他联系人名称
    private String otherConnName;

    //其他联系人电话
    private String otherCoonTel;

    //与联系人关系
    private String otherCoonType;

    private String otherCoonTypeHH;

    private Date createTime;

    private Date updateTime;

    //状态（0停用,1启用）
    private Short status;

    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getConnTypeHH() {
        return connTypeHH;
    }

    public void setConnTypeHH(String connTypeHH) {
        this.connTypeHH = connTypeHH;
    }

    public String getOtherCoonTypeHH() {
        return otherCoonTypeHH;
    }

    public void setOtherCoonTypeHH(String otherCoonTypeHH) {
        this.otherCoonTypeHH = otherCoonTypeHH;
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getConnName() {
        return connName;
    }

    public void setConnName(String connName) {
        this.connName = connName == null ? null : connName.trim();
    }

    public String getConnTel() {
        return connTel;
    }

    public void setConnTel(String connTel) {
        this.connTel = connTel == null ? null : connTel.trim();
    }

    public String getConnType() {
        return connType;
    }

    public void setConnType(String connType) {
        this.connType = connType == null ? null : connType.trim();
    }

    public String getOtherConnName() {
        return otherConnName;
    }

    public void setOtherConnName(String otherConnName) {
        this.otherConnName = otherConnName == null ? null : otherConnName.trim();
    }

    public String getOtherCoonTel() {
        return otherCoonTel;
    }

    public void setOtherCoonTel(String otherCoonTel) {
        this.otherCoonTel = otherCoonTel == null ? null : otherCoonTel.trim();
    }

    public String getOtherCoonType() {
        return otherCoonType;
    }

    public void setOtherCoonType(String otherCoonType) {
        this.otherCoonType = otherCoonType == null ? null : otherCoonType.trim();
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