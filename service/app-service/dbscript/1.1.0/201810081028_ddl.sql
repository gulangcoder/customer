/*
Navicat MySQL Data Transfer

Source Server         : 192.168.102.188
Source Server Version : 50723
Source Host           : 192.168.102.188:3306
Source Database       : zw_fin

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-10-08 10:28:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ent_cust_bank
-- ----------------------------
DROP TABLE IF EXISTS `ent_cust_bank`;
CREATE TABLE `ent_cust_bank` (
  `id` varchar(50) NOT NULL,
  `cust_id` varchar(50) DEFAULT NULL COMMENT '用户详情表id',
  `bank_no` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `bank_type` smallint(2) DEFAULT NULL COMMENT '银行卡类型0:对公,1:对私',
  `bank_bind_name` varchar(200) DEFAULT NULL COMMENT '收款人姓名 ',
  `IDCard` varchar(50) DEFAULT NULL COMMENT '身份证',
  `bank_name` varchar(50) DEFAULT NULL COMMENT '银行名称',
  `bank_code` varchar(50) DEFAULT NULL COMMENT '银行编码',
  `bank_branch_name` varchar(50) DEFAULT NULL COMMENT '支行名称',
  `province_id` varchar(50) DEFAULT NULL COMMENT '省id',
  `province_name` varchar(50) DEFAULT NULL COMMENT '省名称',
  `city_id` varchar(50) DEFAULT NULL COMMENT '市id',
  `city_name` varchar(50) DEFAULT NULL COMMENT '市名称',
  `area_id` varchar(50) DEFAULT NULL COMMENT '区id',
  `area_name` varchar(50) DEFAULT NULL COMMENT '区名称',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `status` smallint(2) DEFAULT NULL COMMENT '状态（1启用，0停用,2绑卡中）',
  `requestno` varchar(50) DEFAULT NULL COMMENT '绑卡业务号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户银行卡表';

-- ----------------------------
-- Table structure for ent_cust_file
-- ----------------------------
DROP TABLE IF EXISTS `ent_cust_file`;
CREATE TABLE `ent_cust_file` (
  `id` varchar(50) NOT NULL COMMENT '客户文件表主键id',
  `cust_id` varchar(50) DEFAULT NULL COMMENT '客户详情表id',
  `file_type` varchar(50) DEFAULT NULL COMMENT '文件类型(数据字典itemCode=fileType)',
  `file_url` varchar(255) DEFAULT NULL COMMENT '文件地址',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` smallint(6) DEFAULT NULL COMMENT '状态（0停用,1启用）',
  `product_id` varchar(50) DEFAULT NULL COMMENT '产品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户文件表';

-- ----------------------------
-- Table structure for ent_cust_product_apply
-- ----------------------------
DROP TABLE IF EXISTS `ent_cust_product_apply`;
CREATE TABLE `ent_cust_product_apply` (
  `id` varchar(50) NOT NULL COMMENT '客户产品申请表主键',
  `cust_id` varchar(50) DEFAULT NULL COMMENT '客户表详情表id',
  `product_id` varchar(50) DEFAULT NULL COMMENT '产品系列表id',
  `apply_status` smallint(2) DEFAULT NULL COMMENT '产品申请状态(0未申请,1申请中，2授信中，3申请成功，4申请失败)',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` smallint(2) DEFAULT NULL COMMENT '步骤 1身份认证 2个人信息 3联系人 4影像资料 5人脸识别 6提交',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户产品申请表';

-- ----------------------------
-- Table structure for ent_customer
-- ----------------------------
DROP TABLE IF EXISTS `ent_customer`;
CREATE TABLE `ent_customer` (
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `cust_id` varchar(50) DEFAULT NULL COMMENT '客户详情表id',
  `company_code` varchar(50) DEFAULT NULL COMMENT '公司编码',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机号，登录账号',
  `password` varchar(255) DEFAULT NULL COMMENT '登录密码',
  `error_count` int(11) DEFAULT NULL COMMENT '登录错误次数',
  `origin` int(11) DEFAULT NULL COMMENT '来源 0:安卓 1:IOS',
  `is_black` int(11) DEFAULT NULL COMMENT '是否黑户 0:是 1:否',
  `status` int(11) DEFAULT NULL COMMENT '状态 0:正常 1:锁定 2:冻结',
  `lock_time` datetime DEFAULT NULL COMMENT '锁定时间',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户表';

-- ----------------------------
-- Table structure for ent_customer_face
-- ----------------------------
DROP TABLE IF EXISTS `ent_customer_face`;
CREATE TABLE `ent_customer_face` (
  `id` varchar(50) NOT NULL COMMENT '人脸识别主键id',
  `cust_id` varchar(50) DEFAULT NULL COMMENT '客户详情表id',
  `company_code` varchar(50) DEFAULT NULL COMMENT '公司编码',
  `face_photo` varchar(255) DEFAULT NULL COMMENT '人脸照片',
  `face_video` varchar(255) DEFAULT NULL COMMENT '人脸视频',
  `face_live_rate` varchar(50) DEFAULT NULL COMMENT '活体检测得分',
  `face_similarity` varchar(50) DEFAULT NULL COMMENT '人脸比对得分',
  `face_status` varchar(10) DEFAULT NULL COMMENT '人脸识别是否通过: 0未通过, 1已通过',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` varchar(10) DEFAULT NULL COMMENT '状态 0 停用 1 启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ent_customer_info
-- ----------------------------
DROP TABLE IF EXISTS `ent_customer_info`;
CREATE TABLE `ent_customer_info` (
  `cust_id` varchar(50) NOT NULL COMMENT '客户详情表主键',
  `company_code` varchar(50) DEFAULT NULL COMMENT '公司编码',
  `cust_num` varchar(50) DEFAULT NULL COMMENT '客户编号',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `idcard_num` varchar(50) DEFAULT NULL COMMENT '身份证号码',
  `sex` smallint(2) DEFAULT NULL COMMENT '性别（0男，1女）',
  `nation` varchar(50) DEFAULT NULL COMMENT '民族',
  `age` varchar(50) DEFAULT NULL COMMENT '出生年月',
  `idcard_address` varchar(255) DEFAULT NULL COMMENT '身份证上居住地址',
  `marry_status` smallint(2) DEFAULT NULL COMMENT '婚姻状态(0未婚,1已婚，3离异，4丧偶，5其他)',
  `residence_type` smallint(2) DEFAULT NULL COMMENT '户口性质(0农村户口，1城市户口)',
  `house_type` varchar(50) DEFAULT NULL COMMENT '住房情况(数据字典itemCode=houseType)',
  `address` varchar(255) DEFAULT NULL COMMENT '居住地址',
  `detail_address` varchar(255) DEFAULT NULL COMMENT '用户详细地址',
  `province_id` varchar(255) DEFAULT NULL COMMENT '省的id',
  `province_name` varchar(255) DEFAULT NULL COMMENT '省',
  `city_id` varchar(255) DEFAULT NULL COMMENT '市的id',
  `city_name` varchar(255) DEFAULT NULL COMMENT '市',
  `area_id` varchar(255) DEFAULT NULL COMMENT '区id',
  `area_name` varchar(255) DEFAULT NULL COMMENT '区',
  `is_student` smallint(2) DEFAULT NULL COMMENT '是否学生(1是;0否)',
  `educational_type` varchar(50) DEFAULT NULL COMMENT '学历（数据字典itemCode=educationalType）',
  `email` varchar(200) DEFAULT NULL COMMENT '电子邮箱',
  `work_type` varchar(50) DEFAULT NULL COMMENT '工作状态(数据字典itemCode=workType)',
  `vacation_type` varchar(50) DEFAULT NULL COMMENT '职业（数据字典itemCode=vacationType）',
  `work_unit` varchar(255) DEFAULT NULL COMMENT '工作单位',
  `work_position` varchar(50) DEFAULT NULL COMMENT '工作职务(数据字典itemCode=workPosition)',
  `work_tel` varchar(50) DEFAULT NULL COMMENT '单位电话',
  `work_address` varchar(255) DEFAULT NULL COMMENT '单位地址',
  `work_detail_address` varchar(255) DEFAULT NULL COMMENT '单位详细地址',
  `work_province_id` varchar(255) DEFAULT NULL COMMENT '单位省id',
  `work_province_name` varchar(255) DEFAULT NULL COMMENT '单位省',
  `work_city_id` varchar(255) DEFAULT NULL COMMENT '单位市id',
  `work_city_name` varchar(255) DEFAULT NULL COMMENT '单位市',
  `work_area_id` varchar(255) DEFAULT NULL COMMENT '单位区id',
  `work_area_name` varchar(255) DEFAULT NULL COMMENT '单位区',
  `include` varchar(200) DEFAULT NULL COMMENT '月收入',
  `nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(255) DEFAULT NULL COMMENT '头像',
  `auth_status` smallint(2) DEFAULT NULL COMMENT '授信状态 0:是 1:否',
  `realname_verify` smallint(2) DEFAULT NULL COMMENT '实名认证 0:是 1:否',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户详情表';

-- ----------------------------
-- Table structure for ent_customer_relationship
-- ----------------------------
DROP TABLE IF EXISTS `ent_customer_relationship`;
CREATE TABLE `ent_customer_relationship` (
  `id` varchar(50) NOT NULL COMMENT '客户联系人表主键',
  `cust_id` varchar(50) DEFAULT NULL COMMENT '客户表详情表id',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编码',
  `conn_name` varchar(50) DEFAULT NULL COMMENT '联系人名称',
  `conn_tel` varchar(50) DEFAULT NULL COMMENT '联系人电话',
  `conn_type` varchar(50) DEFAULT NULL COMMENT '与联系人关系 (数据字典itemCode=connType)',
  `other_Conn_name` varchar(50) DEFAULT NULL COMMENT '其他联系人名称',
  `other_coon_tel` varchar(50) DEFAULT NULL COMMENT '其他联系人电话',
  `other_coon_type` varchar(50) DEFAULT NULL COMMENT '与联系人关系 (数据字典itemCode=connType)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` smallint(6) DEFAULT NULL COMMENT '状态（0停用,1启用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户联系人表';

-- ----------------------------
-- Table structure for task_dispersed_lock
-- ----------------------------
DROP TABLE IF EXISTS `task_dispersed_lock`;
CREATE TABLE `task_dispersed_lock` (
  `className` varchar(32) NOT NULL COMMENT '任务类名',
  `utime` bigint(20) NOT NULL COMMENT '任务执行时的时间',
  PRIMARY KEY (`className`,`utime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
