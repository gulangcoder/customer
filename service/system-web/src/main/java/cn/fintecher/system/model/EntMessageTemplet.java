package cn.fintecher.system.model;

import java.util.Date;

public class EntMessageTemplet {
    private String id;

    private String title;

    private String msgType;

    private Short isPush;

    private Short isPrivateMsg;

    private Short isSendMsg;

    private Short isAccess;

    private String companyCode;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private Short status;

    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Short getIsPush() {
        return isPush;
    }

    public void setIsPush(Short isPush) {
        this.isPush = isPush;
    }

    public Short getIsPrivateMsg() {
        return isPrivateMsg;
    }

    public void setIsPrivateMsg(Short isPrivateMsg) {
        this.isPrivateMsg = isPrivateMsg;
    }

    public Short getIsSendMsg() {
        return isSendMsg;
    }

    public void setIsSendMsg(Short isSendMsg) {
        this.isSendMsg = isSendMsg;
    }

    public Short getIsAccess() {
        return isAccess;
    }

    public void setIsAccess(Short isAccess) {
        this.isAccess = isAccess;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}