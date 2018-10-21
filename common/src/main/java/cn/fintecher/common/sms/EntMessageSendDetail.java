package cn.fintecher.common.sms;

import java.io.Serializable;
import java.util.Date;


public class EntMessageSendDetail implements Serializable{
    private static final long serialVersionUID = -8577246588719638707L;
    private String id;

    //客户id
    private String customerId;
    //客户名称
    private String customerName;
    //客户手机号
    private String customerTel;
    //通知模板id
    private String msgTemplId;
    //通知标题
    private String msgTitle;
    //通知类型
    private String msgType;
    //创建时间
    private Date createTime;
    //通知发送类型
    private String msgSendType;
    //企业编码
    private String companyCode;
    //内容
    private String content;
    //设备号
    private String deviceId;

    ///短信验证码 是否已读 0 已读 1 未读
    private Short haveRead;

    //短信验证码
    private String msgCode;
    //客户银行卡后四位
    private String lastNumber;
    //客户本期还款月日：09-05
    private String monthDay;
    //当前应还款期数
    private String periodsNumber;

    private String money;

    public Short getHaveRead() {
        return haveRead;
    }

    public void setHaveRead(Short haveRead) {
        this.haveRead = haveRead;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPeriodsNumber() {
        return periodsNumber;
    }

    public void setPeriodsNumber(String periodsNumber) {
        this.periodsNumber = periodsNumber;
    }

    public String getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(String monthDay) {
        this.monthDay = monthDay;
    }

    public String getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(String lastNumber) {
        this.lastNumber = lastNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getMsgTemplId() {
        return msgTemplId;
    }

    public void setMsgTemplId(String msgTemplId) {
        this.msgTemplId = msgTemplId;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMsgSendType() {
        return msgSendType;
    }

    public void setMsgSendType(String msgSendType) {
        this.msgSendType = msgSendType;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }
}