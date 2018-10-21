package cn.fintecher.app.model.customer;

import java.io.Serializable;
import java.util.Date;

public class EntCustomerInfo implements Serializable{
    private static final long serialVersionUID = 3502875788009807436L;

    private String custId;

    private String companyCode;

    //客户编号
    private String custNum;

    //真实姓名
    private String realName;

    //身份证号码
    private String idcardNum;

    //性别（0男，1女）
    private Short sex;

    //民族
    private String nation;

    //年龄
    private String age;

    //身份证上居住地址
    private String idcardAddress;

    //婚姻状态(0未婚,1已婚，3离异，4丧偶，5其他)
    private Short marryStatus;

    private String marryStatusHH;

    //户口性质(0农村户口，1城市户口)
    private Short residenceType;

    private String residenceTypeHH;

    //住房情况(数据字典itemCode=houseType)
    private String houseType;

    private String houseTypeHH;

    //居住地址
    private String address;

    //居住详细地址
    private String detailAddress;

    //省的id
    private String provinceId;

    //省
    private String provinceName;

    //市的id
    private String cityId;

    //客户编号
    private String cityName;

    //区id
    private String areaId;

    //区
    private String areaName;

    //是否学生(1是;0否)
    private Short isStudent;

    private String isStudentHH;

    //学历（数据字典itemCode=educationalType）
    private String educationalType;

    private String educationalTypeHH;

    //电子邮箱
    private String email;

    //工作状态(数据字典itemCode=workType)
    private String workType;

    private String workTypeHH;

    //职业（数据字典itemCode=vacationType）
    private String vacationType;

    private String vacationTypeHH;

    //工作单位
    private String workUnit;

    //工作职务(数据字典itemCode=workPosition)
    private String workPosition;

    private String workPositionHH;

    //单位电话
    private String workTel;

    //单位地址
    private String workAddress;

    //单位详细地址
    private String workDetailAddress;

    //单位省id
    private String workProvinceId;

    //单位省
    private String workProvinceName;

    //单位市id
    private String workCityId;

    //单位市
    private String workCityName;

    //单位区id
    private String workAreaId;

    //单位区
    private String workAreaName;

    //月收入
    private String include;

    private String includeHH;

    //昵称
    private String nickName;

    //头像
    private String headImg;

    //授信状态 0:是 1:否
    private Short authStatus;

    //实名认证 0:是 1:否
    private Short realnameVerify;

    private Date createTime;

    private Date updateTime;

    private String phone;

    //当前借款状态 0无欠款 1还款中 2已逾期
    private int loanState;

    private String appAlias;//app别名，用于极光推送

    public String getAppAlias() {
        return appAlias;
    }

    public void setAppAlias(String appAlias) {
        this.appAlias = appAlias;
    }

    public int getLoanState() {
        return loanState;
    }

    public void setLoanState(int loanState) {
        this.loanState = loanState;
    }

    private String productId;

    private Integer noRead;

    public Integer getNoRead() {
        return noRead;
    }

    public void setNoRead(Integer noRead) {
        this.noRead = noRead;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMarryStatusHH() {
        return marryStatusHH;
    }

    public void setMarryStatusHH(String marryStatusHH) {
        this.marryStatusHH = marryStatusHH;
    }

    public String getResidenceTypeHH() {
        return residenceTypeHH;
    }

    public void setResidenceTypeHH(String residenceTypeHH) {
        this.residenceTypeHH = residenceTypeHH;
    }

    public String getHouseTypeHH() {
        return houseTypeHH;
    }

    public void setHouseTypeHH(String houseTypeHH) {
        this.houseTypeHH = houseTypeHH;
    }

    public String getIsStudentHH() {
        return isStudentHH;
    }

    public void setIsStudentHH(String isStudentHH) {
        this.isStudentHH = isStudentHH;
    }

    public String getEducationalTypeHH() {
        return educationalTypeHH;
    }

    public void setEducationalTypeHH(String educationalTypeHH) {
        this.educationalTypeHH = educationalTypeHH;
    }

    public String getWorkTypeHH() {
        return workTypeHH;
    }

    public void setWorkTypeHH(String workTypeHH) {
        this.workTypeHH = workTypeHH;
    }

    public String getVacationTypeHH() {
        return vacationTypeHH;
    }

    public void setVacationTypeHH(String vacationTypeHH) {
        this.vacationTypeHH = vacationTypeHH;
    }

    public String getWorkPositionHH() {
        return workPositionHH;
    }

    public void setWorkPositionHH(String workPositionHH) {
        this.workPositionHH = workPositionHH;
    }

    public String getIncludeHH() {
        return includeHH;
    }

    public void setIncludeHH(String includeHH) {
        this.includeHH = includeHH;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getCustNum() {
        return custNum;
    }

    public void setCustNum(String custNum) {
        this.custNum = custNum == null ? null : custNum.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getIdcardNum() {
        return idcardNum;
    }

    public void setIdcardNum(String idcardNum) {
        this.idcardNum = idcardNum == null ? null : idcardNum.trim();
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIdcardAddress() {
        return idcardAddress;
    }

    public void setIdcardAddress(String idcardAddress) {
        this.idcardAddress = idcardAddress == null ? null : idcardAddress.trim();
    }

    public Short getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(Short marryStatus) {
        this.marryStatus = marryStatus;
    }

    public Short getResidenceType() {
        return residenceType;
    }

    public void setResidenceType(Short residenceType) {
        this.residenceType = residenceType;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType == null ? null : houseType.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId == null ? null : provinceId.trim();
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId == null ? null : cityId.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public Short getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Short isStudent) {
        this.isStudent = isStudent;
    }

    public String getEducationalType() {
        return educationalType;
    }

    public void setEducationalType(String educationalType) {
        this.educationalType = educationalType == null ? null : educationalType.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType == null ? null : workType.trim();
    }

    public String getVacationType() {
        return vacationType;
    }

    public void setVacationType(String vacationType) {
        this.vacationType = vacationType == null ? null : vacationType.trim();
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit == null ? null : workUnit.trim();
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition == null ? null : workPosition.trim();
    }

    public String getWorkTel() {
        return workTel;
    }

    public void setWorkTel(String workTel) {
        this.workTel = workTel == null ? null : workTel.trim();
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress == null ? null : workAddress.trim();
    }

    public String getWorkDetailAddress() {
        return workDetailAddress;
    }

    public void setWorkDetailAddress(String workDetailAddress) {
        this.workDetailAddress = workDetailAddress;
    }

    public String getWorkProvinceId() {
        return workProvinceId;
    }

    public void setWorkProvinceId(String workProvinceId) {
        this.workProvinceId = workProvinceId;
    }

    public String getWorkProvinceName() {
        return workProvinceName;
    }

    public void setWorkProvinceName(String workProvinceName) {
        this.workProvinceName = workProvinceName;
    }

    public String getWorkCityId() {
        return workCityId;
    }

    public void setWorkCityId(String workCityId) {
        this.workCityId = workCityId;
    }

    public String getWorkCityName() {
        return workCityName;
    }

    public void setWorkCityName(String workCityName) {
        this.workCityName = workCityName;
    }

    public String getWorkAreaId() {
        return workAreaId;
    }

    public void setWorkAreaId(String workAreaId) {
        this.workAreaId = workAreaId;
    }

    public String getWorkAreaName() {
        return workAreaName;
    }

    public void setWorkAreaName(String workAreaName) {
        this.workAreaName = workAreaName;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include == null ? null : include.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    public Short getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Short authStatus) {
        this.authStatus = authStatus;
    }

    public Short getRealnameVerify() {
        return realnameVerify;
    }

    public void setRealnameVerify(Short realnameVerify) {
        this.realnameVerify = realnameVerify;
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
}