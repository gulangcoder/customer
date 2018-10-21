package cn.fintecher.app.model.customer;

import java.io.Serializable;
import java.util.Date;

public class EntCustomerFace implements Serializable{
    private static final long serialVersionUID = 2230782025675917852L;
    private String id;

    private String custId;

    private String companyCode;

    private String facePhoto;

    private String faceVideo;

    private String faceLiveRate;

    private String faceSimilarity;

    private String faceStatus;

    private Date createTime;

    private Date updateTime;

    private String status;

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

    public String getFacePhoto() {
        return facePhoto;
    }

    public void setFacePhoto(String facePhoto) {
        this.facePhoto = facePhoto == null ? null : facePhoto.trim();
    }

    public String getFaceVideo() {
        return faceVideo;
    }

    public void setFaceVideo(String faceVideo) {
        this.faceVideo = faceVideo == null ? null : faceVideo.trim();
    }

    public String getFaceLiveRate() {
        return faceLiveRate;
    }

    public void setFaceLiveRate(String faceLiveRate) {
        this.faceLiveRate = faceLiveRate == null ? null : faceLiveRate.trim();
    }

    public String getFaceSimilarity() {
        return faceSimilarity;
    }

    public void setFaceSimilarity(String faceSimilarity) {
        this.faceSimilarity = faceSimilarity == null ? null : faceSimilarity.trim();
    }

    public String getFaceStatus() {
        return faceStatus;
    }

    public void setFaceStatus(String faceStatus) {
        this.faceStatus = faceStatus == null ? null : faceStatus.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}