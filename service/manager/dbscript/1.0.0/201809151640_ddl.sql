/*
Navicat MySQL Data Transfer

Source Server         : 192.168.102.188
Source Server Version : 50723
Source Host           : 192.168.102.188:3306
Source Database       : zw_fin

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-09-15 16:45:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `company_profile`
-- ----------------------------
DROP TABLE IF EXISTS `company_profile`;
CREATE TABLE `company_profile` (
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `company_code` varchar(50) NOT NULL COMMENT '企业编号',
  `content` longtext COMMENT '公司简介',
  `delete_flag` smallint(6) DEFAULT NULL COMMENT '删除标识 0 未删除 1 已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company_profile
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_contract_book_detail`
-- ----------------------------
DROP TABLE IF EXISTS `ent_contract_book_detail`;
CREATE TABLE `ent_contract_book_detail` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `contract_book_no` varchar(200) DEFAULT NULL COMMENT '合同签订编号',
  `content` longtext COMMENT '合同内容',
  `contract_templ_id` varchar(50) DEFAULT NULL COMMENT '合同模板表主键',
  `contract_title` varchar(200) DEFAULT NULL COMMENT '合同标题',
  `contract_type` varchar(50) DEFAULT NULL COMMENT '合同类型(数据字典itemCode=contractType）',
  `customer_id` varchar(50) DEFAULT NULL COMMENT '用户表主键',
  `customer_name` varchar(200) DEFAULT NULL COMMENT '用户名称',
  `customer_card_no` varchar(50) DEFAULT NULL COMMENT '用户身份证号',
  `create_time` datetime DEFAULT NULL COMMENT '合同签订时间',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编码',
  `product_id` varchar(50) DEFAULT NULL COMMENT '产品表主键',
  `status` smallint(2) DEFAULT '0' COMMENT '合同签订状态(0签约中，1签约成功，2签约失败)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同签订表';

-- ----------------------------
-- Records of ent_contract_book_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_contract_templet`
-- ----------------------------
DROP TABLE IF EXISTS `ent_contract_templet`;
CREATE TABLE `ent_contract_templet` (
  `id` varchar(50) NOT NULL DEFAULT '' COMMENT '主键',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `content` longtext COMMENT '内容',
  `contract_type` varchar(50) DEFAULT NULL COMMENT '模板类型(数据字典itemCode=contractType）',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编码',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` smallint(2) DEFAULT NULL COMMENT '状态(1启用;0停用)',
  `version` varchar(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同模板表';

-- ----------------------------
-- Records of ent_contract_templet
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_goods`
-- ----------------------------
DROP TABLE IF EXISTS `ent_goods`;
CREATE TABLE `ent_goods` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `category_id` varchar(50) DEFAULT NULL COMMENT '商品类别ID',
  `brand_id` varchar(50) DEFAULT NULL COMMENT '品牌ID',
  `goods_code` varchar(255) DEFAULT NULL COMMENT '商品货号',
  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `image_url` longtext COMMENT '商品图片地址',
  `logo_url` longtext COMMENT '商品LOGO图片地址',
  `model` varchar(50) DEFAULT NULL COMMENT '商品型号',
  `color` varchar(20) DEFAULT NULL COMMENT '商品颜色',
  `weight` decimal(20,2) DEFAULT NULL COMMENT '商品重量',
  `sale_price` decimal(20,2) DEFAULT NULL COMMENT '销售价格',
  `cost_price` decimal(20,2) DEFAULT NULL COMMENT '成本价',
  `amount` int(11) DEFAULT NULL COMMENT '库存数量',
  `freight` decimal(20,2) DEFAULT '0.00' COMMENT '运费',
  `market_price` decimal(20,2) DEFAULT NULL COMMENT '市场价',
  `unit` varchar(20) DEFAULT NULL COMMENT '商品计量单位',
  `score` int(8) DEFAULT NULL COMMENT '积分',
  `status` smallint(1) DEFAULT NULL COMMENT '0未上架,1已上架,2已删除',
  `minimum_quantity` int(6) DEFAULT NULL COMMENT '最低起订量',
  `params` longtext COMMENT '参数字符串',
  `specs` longtext COMMENT '规格字符串',
  `sequence` int(11) DEFAULT NULL COMMENT '序号',
  `remark` text COMMENT '商品描述',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_point` smallint(2) DEFAULT '0' COMMENT '是否支持积分兑换(0:不支持;1:支持)',
  `point_amount` int(11) DEFAULT '0' COMMENT '积分兑换数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of ent_goods
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_goods_category`
-- ----------------------------
DROP TABLE IF EXISTS `ent_goods_category`;
CREATE TABLE `ent_goods_category` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `category_name` varchar(50) DEFAULT NULL COMMENT '类别名称',
  `category_code` varchar(50) DEFAULT NULL COMMENT '类别CODE',
  `parent_id` varchar(50) DEFAULT NULL COMMENT '父级ID',
  `parent_name` varchar(50) DEFAULT NULL COMMENT '父级NAME',
  `status` smallint(6) DEFAULT NULL COMMENT '0停用,1启用,2删除',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `sequence` int(20) DEFAULT NULL COMMENT '序号',
  `amount` int(20) DEFAULT '0' COMMENT '商品数量',
  `goods_grade` int(2) DEFAULT '1' COMMENT '当前几级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类别表';

-- ----------------------------
-- Records of ent_goods_category
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_goods_image`
-- ----------------------------
DROP TABLE IF EXISTS `ent_goods_image`;
CREATE TABLE `ent_goods_image` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `goods_code` varchar(50) DEFAULT NULL COMMENT '商品编号',
  `image_url` varchar(255) DEFAULT NULL COMMENT '商品图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业商品图片表';

-- ----------------------------
-- Records of ent_goods_image
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_goods_promotion`
-- ----------------------------
DROP TABLE IF EXISTS `ent_goods_promotion`;
CREATE TABLE `ent_goods_promotion` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `goods_code` varchar(50) DEFAULT NULL COMMENT '商品编号',
  `promotion_code` varchar(50) DEFAULT NULL COMMENT '活动编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业商品活动映射表';

-- ----------------------------
-- Records of ent_goods_promotion
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_menu`
-- ----------------------------
DROP TABLE IF EXISTS `ent_menu`;
CREATE TABLE `ent_menu` (
  `id` varchar(50) NOT NULL COMMENT '编号',
  `menu_name` varchar(255) NOT NULL COMMENT '菜单名称',
  `url` varchar(255) NOT NULL COMMENT '菜单路径',
  `sequence` int(20) DEFAULT NULL COMMENT '序号',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `menu_type` tinyint(4) DEFAULT NULL COMMENT '1:菜单,2:按钮3,组件',
  `parent_id` varchar(50) NOT NULL COMMENT '父级菜单id',
  `parent_name` varchar(100) DEFAULT NULL COMMENT '父级菜单名称',
  `company_code` varchar(50) DEFAULT NULL COMMENT '所属企业',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` text COMMENT '备注',
  `status` smallint(6) DEFAULT NULL COMMENT '状态   0：停用, 1：启用',
  `auth_status` smallint(6) DEFAULT '0' COMMENT '是否支持权限分配(0,否,1是)',
  `desensitize_status` smallint(6) DEFAULT '0' COMMENT '是否支持脱敏(0,否,1是)',
  `sys_code` varchar(50) DEFAULT NULL COMMENT '所属系统',
  `sys_menu_code` varchar(50) DEFAULT NULL COMMENT 'sys_module_detail主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';


-- ----------------------------
-- Table structure for `ent_message_send_detail`
-- ----------------------------
DROP TABLE IF EXISTS `ent_message_send_detail`;
CREATE TABLE `ent_message_send_detail` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `customer_id` varchar(50) DEFAULT NULL COMMENT '用户id',
  `customer_name` varchar(200) DEFAULT NULL COMMENT '用户名称 ',
  `customer_tel` varchar(50) DEFAULT NULL COMMENT '用户手机号',
  `msg_templ_id` varchar(50) DEFAULT NULL COMMENT '消息模板id',
  `msg_title` varchar(200) DEFAULT NULL COMMENT '消息标题',
  `content` text COMMENT '消息内容',
  `msg_type` varchar(50) DEFAULT NULL COMMENT '消息类型(数据字典itemCode=msgType)',
  `create_time` datetime DEFAULT NULL COMMENT '发送时间',
  `msg_send_type` varchar(50) DEFAULT NULL COMMENT '消息发送类型(0短信，1站内信)',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编码',
  `have_read` smallint(2) DEFAULT NULL COMMENT '是否已读 0已读 1未读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息发送明细表';

-- ----------------------------
-- Records of ent_message_send_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_message_templet`
-- ----------------------------
DROP TABLE IF EXISTS `ent_message_templet`;
CREATE TABLE `ent_message_templet` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `msg_type` varchar(50) DEFAULT NULL COMMENT '模板类型(数据字典itemCode=msgType)',
  `content` text COMMENT '模板内容',
  `is_push` smallint(2) DEFAULT '0' COMMENT '是否极光推送(1是0否)',
  `is_private_msg` smallint(2) DEFAULT '0' COMMENT '是',
  `Is_send_msg` smallint(2) DEFAULT '0' COMMENT '是否发送短信(1是；0否)',
  `is_access` smallint(2) DEFAULT '0' COMMENT '针对短信是否审核通过：0审核中；1审核成功；2审核失败',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编码',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` smallint(2) DEFAULT NULL COMMENT '状态(1启用；0停用)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息模板表';

-- ----------------------------
-- Records of ent_message_templet
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_module`
-- ----------------------------
DROP TABLE IF EXISTS `ent_module`;
CREATE TABLE `ent_module` (
  `sys_code` varchar(50) NOT NULL COMMENT '主键',
  `sys_name` varchar(200) DEFAULT NULL COMMENT '系统名称',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` smallint(6) DEFAULT NULL COMMENT '状态',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `sys_module_code` varchar(50) DEFAULT NULL COMMENT '系统模块编号',
  `sequence` int(11) DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`sys_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典明细表';



-- ----------------------------
-- Table structure for `ent_organization`
-- ----------------------------
DROP TABLE IF EXISTS `ent_organization`;
CREATE TABLE `ent_organization` (
  `org_id` varchar(50) NOT NULL COMMENT '组织编号',
  `parent_org_id` varchar(50) DEFAULT NULL COMMENT '父级编号',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `org_code` varchar(100) DEFAULT NULL COMMENT '组织编码',
  `org_manager` varchar(128) DEFAULT NULL COMMENT '联系人',
  `org_tel` varchar(18) DEFAULT NULL COMMENT '联系电话(手机号)',
  `org_short_tel` varchar(12) DEFAULT NULL COMMENT '固定电话',
  `org_path` text COMMENT '组织层级  存储当前节点所在的层级，通过使用父级的org_id加上当前的org_id组合存储；如：xxx-xxx|xxxxx-xxxxxx',
  `status` smallint(6) DEFAULT NULL COMMENT '状态（1：启用  0：停用）',
  `org_type` smallint(6) DEFAULT NULL COMMENT '组织机构类型（1=部门;0=公司）',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织架构管理表';


-- ----------------------------
-- Table structure for `ent_product`
-- ----------------------------
DROP TABLE IF EXISTS `ent_product`;
CREATE TABLE `ent_product` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `sequence` int(20) DEFAULT NULL COMMENT '序号',
  `product_series_sequence` varchar(50) DEFAULT NULL COMMENT '产品系列序列',
  `product_series` varchar(50) DEFAULT NULL COMMENT '产品系列',
  `product_image` varchar(255) DEFAULT NULL COMMENT '产品图片',
  `product_min_lines` decimal(12,2) DEFAULT NULL COMMENT '产品最小额度',
  `product_max_lines` decimal(12,2) DEFAULT NULL COMMENT '产品最大额度',
  `applicable_people` varchar(255) DEFAULT NULL COMMENT '试用人群',
  `description` varchar(255) DEFAULT NULL COMMENT '产品描述',
  `recommend` smallint(6) DEFAULT NULL COMMENT '是否首页推荐 0是 1否',
  `status` smallint(6) DEFAULT NULL COMMENT '停用:0,启用:1',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品管理表';

-- ----------------------------
-- Records of ent_product
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_product_company_rate`
-- ----------------------------
DROP TABLE IF EXISTS `ent_product_company_rate`;
CREATE TABLE `ent_product_company_rate` (
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `product_credit_id` varchar(50) DEFAULT NULL COMMENT '产品信率id',
  `company_id` varchar(50) DEFAULT NULL COMMENT '公司id',
  `company_code` varchar(50) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(50) DEFAULT NULL COMMENT '公司名称',
  `rate` decimal(12,2) DEFAULT NULL COMMENT '公司占比比率',
  `delete_flag` smallint(6) DEFAULT NULL COMMENT '删除:1,未删除:0',
  `type` smallint(6) DEFAULT NULL COMMENT '类型: 0逾期违约公司占比 1提前还款公司占比 2保证金 3服务费 4手续费',
  `batch` varchar(255) DEFAULT NULL COMMENT '唯一标识批次，用于记录修改之前的数据',
  `kind` smallint(6) DEFAULT NULL COMMENT '0 组织架构公司 1 其他资金方公司',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品信率公司占比表';

-- ----------------------------
-- Records of ent_product_company_rate
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_product_credit`
-- ----------------------------
DROP TABLE IF EXISTS `ent_product_credit`;
CREATE TABLE `ent_product_credit` (
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `company_code` varchar(50) DEFAULT NULL COMMENT '公司编码',
  `product_id` varchar(50) DEFAULT NULL COMMENT '产品id',
  `product_series` varchar(50) DEFAULT NULL COMMENT '产品系列',
  `product_detail_id` varchar(50) DEFAULT NULL COMMENT '产品详情id',
  `product_sequence` varchar(50) DEFAULT NULL COMMENT '产品序列',
  `product_name` varchar(50) DEFAULT NULL COMMENT '产品名称',
  `serial` int(20) DEFAULT NULL COMMENT '序号',
  `payment_way` varchar(50) DEFAULT NULL COMMENT '0等额本息 1等额本金 2先息后本 3到期一次还本付息 4等本等息',
  `periods` int(20) DEFAULT NULL COMMENT '期数',
  `periods_days` int(20) DEFAULT NULL COMMENT '每期期限',
  `each_term_type` smallint(6) DEFAULT NULL COMMENT '每期期限类型 0日 1周 2月',
  `rate` decimal(12,2) DEFAULT NULL COMMENT '利率',
  `rate_flag` smallint(6) DEFAULT NULL COMMENT '利率标识 0日 1月 2年',
  `contract_rate` decimal(12,2) DEFAULT NULL COMMENT '合同利率',
  `contract_rate_flag` smallint(6) DEFAULT NULL COMMENT '合同利率标识 0日 1月 2年',
  `paymeny_days_type` smallint(6) DEFAULT NULL COMMENT '账期类型 0正常账期 1固定日账期',
  `payment_day` int(12) DEFAULT NULL COMMENT '账期日',
  `contract_overdue_rate` decimal(12,2) DEFAULT NULL COMMENT '合同逾期费用率',
  `credit_protection_days` int(20) DEFAULT NULL COMMENT '征信保护天数',
  `overdue_protection_days` int(20) DEFAULT NULL COMMENT '逾期保护天数',
  `overdue_rate_type` smallint(6) DEFAULT NULL COMMENT '逾期费率类型 0固定费率 1阶梯费率',
  `overdue_rate` decimal(12,2) DEFAULT NULL COMMENT '类型为固定费率的逾期费率',
  `nopromise_company_type` smallint(6) DEFAULT NULL COMMENT '违约是否需要公司占比 0是 1否',
  `penalty_rule` varchar(50) DEFAULT NULL COMMENT '罚息配置 1每期借款本金、2借款本息、3借款总利息、4合同总额\r\n',
  `have_prepayment` smallint(6) DEFAULT NULL COMMENT '是否提前还款  0是 1否',
  `prepayment_rate_type` smallint(6) DEFAULT NULL COMMENT '提前还款违约率类型 0固定费率 1阶梯费率',
  `prepayment_rate` decimal(12,2) DEFAULT NULL COMMENT '类型为固定费率的提前还款违约费率',
  `prepayment_company_type` smallint(6) DEFAULT NULL COMMENT '提前还款是否需要公司占比 0是 1否',
  `prepayment_rule` varchar(50) DEFAULT NULL COMMENT '提前还款规则 借款本金...',
  `have_cash` smallint(6) DEFAULT NULL COMMENT '有无保证金 0有 1无',
  `cash_deposit_type` smallint(6) DEFAULT NULL COMMENT '保证金类型 0固定金额 1比例',
  `cash_deposit` decimal(12,2) DEFAULT NULL COMMENT '保证金或保证金费率',
  `refund_way` varchar(50) DEFAULT NULL COMMENT '退换方式 正常结清后退还...',
  `cash_company_type` smallint(6) DEFAULT NULL COMMENT '保证金是否需要公司占比 0是 1否',
  `cash_deposit_rule` varchar(50) DEFAULT NULL COMMENT '保证金规则',
  `have_service` smallint(6) DEFAULT NULL COMMENT '有无服务费 0有 1无',
  `service_fee_rate` decimal(12,2) DEFAULT NULL COMMENT '分期服务费费率',
  `early_service_fee_rate` decimal(12,2) DEFAULT NULL COMMENT '前期服务器费率',
  `service_fee_rule` varchar(50) DEFAULT NULL COMMENT '服务费规则',
  `service_company_type` smallint(6) DEFAULT NULL COMMENT '服务费是否需要公司占比',
  `have_poundage` smallint(6) DEFAULT NULL COMMENT '有无手续费 0有 1无',
  `poundage` decimal(12,2) DEFAULT NULL COMMENT '手续费',
  `poundage_company_type` smallint(6) DEFAULT NULL COMMENT '手续费是否需要公司占比',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` smallint(6) DEFAULT NULL COMMENT '停用:0,启用:1',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_flag` smallint(6) DEFAULT NULL COMMENT '删除:1,未删除:0',
  `batch` varchar(255) DEFAULT NULL COMMENT '唯一标识批次，用于记录修改之前的数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品信贷费率表';

-- ----------------------------
-- Records of ent_product_credit
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_product_detail`
-- ----------------------------
DROP TABLE IF EXISTS `ent_product_detail`;
CREATE TABLE `ent_product_detail` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `product_id` varchar(50) DEFAULT NULL COMMENT '产品系列序列id',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `sequence` int(20) DEFAULT NULL COMMENT '序号',
  `product_sequence` varchar(50) DEFAULT NULL COMMENT '产品序列',
  `product_name` varchar(50) DEFAULT NULL COMMENT '产品名称',
  `product_image` varchar(255) DEFAULT NULL COMMENT '产品图片',
  `product_min_lines` decimal(12,2) DEFAULT NULL COMMENT '产品最小额度',
  `product_max_lines` decimal(12,2) DEFAULT NULL COMMENT '产品最大额度',
  `credit_validity` int(20) DEFAULT NULL COMMENT '授信有效期',
  `credit_flag` smallint(6) DEFAULT NULL COMMENT '授信有效期标识 0:日 1:月',
  `applicable_people` varchar(255) DEFAULT NULL COMMENT '使用人群',
  `product_description` varchar(255) DEFAULT NULL COMMENT '产品描述',
  `withholding_schedule` smallint(6) DEFAULT NULL COMMENT '是否支持按期代扣 0:是 1:否',
  `payments_schedule` smallint(6) DEFAULT NULL COMMENT '是否支持按期还款 0:是 1:否',
  `prepayment` smallint(6) DEFAULT NULL COMMENT '是否支持提前还款 0:是 1:否',
  `shadow_data` varchar(255) DEFAULT NULL COMMENT '影像资料',
  `associated_company` varchar(255) DEFAULT NULL COMMENT '关联公司',
  `status` smallint(6) DEFAULT NULL COMMENT '停用:0,启用:1',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品详情表';

-- ----------------------------
-- Records of ent_product_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_product_detail_company`
-- ----------------------------
DROP TABLE IF EXISTS `ent_product_detail_company`;
CREATE TABLE `ent_product_detail_company` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `product_id` varchar(50) DEFAULT NULL COMMENT '产品id',
  `company_id` varchar(50) DEFAULT NULL COMMENT '关联公司id',
  `company_code` varchar(50) NOT NULL COMMENT '关联公司编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(64) DEFAULT NULL COMMENT '修改人',
  `deleted_flag` smallint(6) DEFAULT NULL COMMENT '停用:0,启用:1,逻辑删除标识',
  `type` smallint(6) DEFAULT NULL COMMENT '0 组织架构公司 1 其他资金方',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品详情关联公司表';

-- ----------------------------
-- Records of ent_product_detail_company
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_product_ladder`
-- ----------------------------
DROP TABLE IF EXISTS `ent_product_ladder`;
CREATE TABLE `ent_product_ladder` (
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `company_code` varchar(50) DEFAULT NULL COMMENT '公司编码',
  `product_credit_id` varchar(50) DEFAULT NULL COMMENT '产品信率id',
  `min_days` int(20) DEFAULT NULL COMMENT '天数范围起',
  `max_days` int(20) DEFAULT NULL COMMENT '天数范围止',
  `amount_rate` decimal(12,2) DEFAULT NULL COMMENT '费率',
  `delete_flag` smallint(6) DEFAULT NULL COMMENT '删除:1,未删除:0',
  `type` smallint(6) DEFAULT NULL COMMENT '类型 0:逾期违约阶梯 1:提前还款阶梯',
  `batch` varchar(255) DEFAULT NULL COMMENT '唯一标识批次，用于记录修改之前的数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品信率阶梯费率表';

-- ----------------------------
-- Records of ent_product_ladder
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_product_video`
-- ----------------------------
DROP TABLE IF EXISTS `ent_product_video`;
CREATE TABLE `ent_product_video` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `product_id` varchar(50) DEFAULT NULL COMMENT '产品id',
  `video_type` varchar(50) NOT NULL COMMENT '影像资料类型-关联videoType字典',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(64) DEFAULT NULL COMMENT '修改人',
  `deleted_flag` varchar(5) DEFAULT NULL COMMENT '停用:0,启用:1,逻辑删除标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品详情影像资料表';

-- ----------------------------
-- Records of ent_product_video
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_promotion`
-- ----------------------------
DROP TABLE IF EXISTS `ent_promotion`;
CREATE TABLE `ent_promotion` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `promotion_name` varchar(200) DEFAULT NULL COMMENT '活动名称',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` smallint(6) DEFAULT NULL COMMENT '状态（0-停用，1-启用）',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `start_time` datetime DEFAULT NULL COMMENT '活动开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '活动结束时间',
  `promotion_type` varchar(50) DEFAULT NULL COMMENT 'promotion_type:活动类型 discount：满折 reduce：满减 gift：满赠',
  `promotion_pattern` varchar(10) DEFAULT NULL COMMENT 'promotion_pattern:活动方式\r\n            num：数量\r\n            money：金额',
  `reservation` smallint(6) DEFAULT NULL COMMENT '是否订制（1：是，0：否）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动配置表';

-- ----------------------------
-- Records of ent_promotion
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_promotion_rule`
-- ----------------------------
DROP TABLE IF EXISTS `ent_promotion_rule`;
CREATE TABLE `ent_promotion_rule` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `condit` text COMMENT '条件',
  `result` text COMMENT '结果',
  `promotion_code` varchar(50) DEFAULT NULL COMMENT '所属活动',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动配置规则表';

-- ----------------------------
-- Records of ent_promotion_rule
-- ----------------------------

-- ----------------------------
-- Table structure for `ent_role`
-- ----------------------------
DROP TABLE IF EXISTS `ent_role`;
CREATE TABLE `ent_role` (
  `role_id` varchar(50) NOT NULL COMMENT '角色编号',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `org_id` varchar(50) DEFAULT NULL COMMENT '组织编号',
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `status` smallint(6) DEFAULT NULL COMMENT '状态  1：启用  0：停用',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `role_code` varchar(50) DEFAULT NULL COMMENT '角色编码',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色管理表';


-- ----------------------------
-- Table structure for `ent_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `ent_role_menu`;
CREATE TABLE `ent_role_menu` (
  `id` varchar(50) NOT NULL COMMENT '系统编号',
  `role_id` varchar(50) DEFAULT NULL COMMENT '角色编号',
  `org_id` varchar(50) DEFAULT NULL COMMENT '组织编号',
  `menu_id` varchar(50) DEFAULT NULL COMMENT '菜单资源编号',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理表';


-- ----------------------------
-- Table structure for `ent_user`
-- ----------------------------
DROP TABLE IF EXISTS `ent_user`;
CREATE TABLE `ent_user` (
  `user_id` varchar(50) NOT NULL COMMENT '用户编号',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `org_id` varchar(50) DEFAULT NULL COMMENT '组织编号',
  `employee_id` varchar(50) DEFAULT NULL COMMENT '员工编号',
  `account` varchar(255) NOT NULL COMMENT '登录账号',
  `password` varchar(255) DEFAULT NULL COMMENT '登录密码',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `sex` smallint(6) DEFAULT NULL COMMENT '性别:1男，２女',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `tel` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `qq` varchar(30) DEFAULT NULL COMMENT 'QQ',
  `status` smallint(6) DEFAULT NULL COMMENT '状态  1：启用 0：停用2：锁定（在密码错误达到一定次数时，进行锁定）',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `role_id` varchar(50) DEFAULT NULL COMMENT '角色id',
  `lock_time` datetime DEFAULT NULL COMMENT '锁定时间',
  `latest_time` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `latest_ip` varchar(25) DEFAULT NULL COMMENT '最后一次登录IP',
  `error_count` int(11) DEFAULT NULL COMMENT '错误次数（通过手动解锁来重置错误次数）',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_USER` (`user_id`,`company_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业用户管理表';


-- ----------------------------
-- Table structure for `ent_user_data_permission`
-- ----------------------------
DROP TABLE IF EXISTS `ent_user_data_permission`;
CREATE TABLE `ent_user_data_permission` (
  `id` varchar(50) NOT NULL COMMENT '系统编号',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户编号',
  `org_id` varchar(50) DEFAULT NULL COMMENT '组织编号',
  `menu_id` varchar(50) DEFAULT NULL COMMENT '菜单资源编号',
  `is_desensite` smallint(6) DEFAULT '0' COMMENT '是否脱敏 1：是 0：否(默认值)',
  `org_path` text COMMENT '允许查看数据的层级，用来存储可以查看当前这个资源的数据权限',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限管理表';

-- ----------------------------
-- Records of ent_user_data_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_city`
-- ----------------------------
DROP TABLE IF EXISTS `sys_city`;
CREATE TABLE `sys_city` (
  `id` varchar(40) NOT NULL COMMENT '唯一标识 (城市表)',
  `city_name` varchar(100) DEFAULT NULL COMMENT '城市名称',
  `short_name` varchar(40) DEFAULT NULL COMMENT '简称',
  `province_id` varchar(40) DEFAULT NULL COMMENT '省份ID',
  `create_time` varchar(40) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(40) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `sys_dict_detail`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `item_code` varchar(50) NOT NULL COMMENT '字典编号',
  `detail_code` varchar(50) DEFAULT NULL COMMENT '明细code',
  `detail_name` varchar(255) NOT NULL COMMENT '字典名称',
  `status` smallint(6) DEFAULT NULL COMMENT '停用:0,启用:1,删除:2',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典明细表';


-- ----------------------------
-- Table structure for `sys_dict_item`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
  `id` varchar(50) NOT NULL COMMENT '编号',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `item_code` varchar(50) NOT NULL COMMENT '字典编号',
  `item_name` varchar(255) NOT NULL COMMENT '字典名称',
  `status` smallint(6) DEFAULT NULL COMMENT '停用:0,启用:1',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典项表';


-- ----------------------------
-- Table structure for `sys_district`
-- ----------------------------
DROP TABLE IF EXISTS `sys_district`;
CREATE TABLE `sys_district` (
  `id` decimal(10,0) NOT NULL COMMENT '三级联动-区县级表',
  `city_id` decimal(10,0) DEFAULT NULL COMMENT '城市id',
  `district_name` varchar(100) DEFAULT NULL COMMENT '地区名称',
  `short_name` varchar(40) DEFAULT NULL COMMENT '简称',
  `create_time` varchar(40) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(40) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_district_id` (`id`) USING BTREE,
  KEY `sys_district_city_id` (`city_id`) USING BTREE,
  KEY `sys_district_district_name` (`district_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `sys_enterprise`
-- ----------------------------
DROP TABLE IF EXISTS `sys_enterprise`;
CREATE TABLE `sys_enterprise` (
  `company_code` varchar(50) NOT NULL COMMENT '企业编号',
  `company_name` varchar(128) DEFAULT NULL COMMENT '企业名称',
  `email` varchar(100) DEFAULT NULL COMMENT '企业邮箱',
  `tel` varchar(18) DEFAULT NULL COMMENT '企业联系电话',
  `company_manager` varchar(128) DEFAULT NULL COMMENT '联系人',
  `org_img` varchar(128) DEFAULT NULL COMMENT '营业执照',
  `org_owner_img` varchar(128) DEFAULT NULL COMMENT '法人身份证',
  `status` smallint(6) DEFAULT NULL COMMENT '企业状态 1：启用（默认值） 0：停用',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `short_name` varchar(128) DEFAULT NULL COMMENT '企业简称',
  `wechat_no` varchar(100) DEFAULT NULL COMMENT '微信号',
  PRIMARY KEY (`company_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业信息管理';


-- ----------------------------
-- Table structure for `sys_ent_other_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_ent_other_config`;
CREATE TABLE `sys_ent_other_config` (
  `id` varchar(50) NOT NULL,
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编码',
  `other_id` varchar(50) DEFAULT NULL COMMENT '三方id',
  `other_name` varchar(200) DEFAULT NULL COMMENT '三方名称',
  `create_user` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业三方配置关联表';



-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(50) NOT NULL COMMENT '编号',
  `menu_name` varchar(255) NOT NULL COMMENT '菜单名称',
  `url` varchar(255) NOT NULL COMMENT '菜单路径',
  `sequence` int(20) DEFAULT NULL COMMENT '序号',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `menu_type` tinyint(4) DEFAULT NULL COMMENT '1:菜单,2:按钮3,组件',
  `parent_id` varchar(50) NOT NULL COMMENT '父级菜单id',
  `parent_name` varchar(100) DEFAULT NULL COMMENT '父级菜单名称',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` text COMMENT '备注',
  `status` smallint(6) DEFAULT NULL COMMENT '状态   0：停用, 1：启用',
  `auth_status` smallint(6) DEFAULT NULL COMMENT '是否支持权限分配(0,否,1是)',
  `desensitize_status` smallint(6) DEFAULT NULL COMMENT '是否支持脱敏(0,否,1是)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';


-- ----------------------------
-- Table structure for `sys_module`
-- ----------------------------
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module` (
  `sys_code` varchar(50) NOT NULL COMMENT '主键',
  `sys_name` varchar(200) DEFAULT NULL COMMENT '系统名称',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` smallint(6) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`sys_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统模块';



-- ----------------------------
-- Table structure for `sys_module_detail`
-- ----------------------------
DROP TABLE IF EXISTS `sys_module_detail`;
CREATE TABLE `sys_module_detail` (
  `id` varchar(50) NOT NULL COMMENT '编号',
  `menu_name` varchar(255) NOT NULL COMMENT '菜单名称',
  `url` varchar(255) NOT NULL COMMENT '菜单路径',
  `sequence` int(20) DEFAULT NULL COMMENT '序号',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `menu_type` tinyint(4) DEFAULT NULL COMMENT '1:菜单,2:按钮3,组件',
  `parent_id` varchar(50) NOT NULL COMMENT '父级菜单id',
  `parent_name` varchar(100) DEFAULT NULL COMMENT '父级菜单名称',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` text COMMENT '备注',
  `status` smallint(6) DEFAULT NULL COMMENT '状态   0：停用, 1：启用',
  `auth_status` smallint(6) DEFAULT NULL COMMENT '是否支持权限分配(0,否,1是)',
  `desensitize_status` smallint(6) DEFAULT NULL COMMENT '是否支持脱敏(0,否,1是)',
  `sys_code` varchar(50) DEFAULT NULL,
  `sys_menu_code` varchar(50) DEFAULT NULL COMMENT '菜单主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台系统菜单映射表';


-- ----------------------------
-- Table structure for `sys_other_columns_conf`
-- ----------------------------
DROP TABLE IF EXISTS `sys_other_columns_conf`;
CREATE TABLE `sys_other_columns_conf` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `other_id` varchar(50) DEFAULT NULL,
  `sortting` int(11) DEFAULT NULL COMMENT '排序值',
  `para_code` varchar(100) DEFAULT NULL COMMENT '参数编码',
  `para_name` varchar(200) DEFAULT NULL COMMENT '参数名称 ',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` smallint(2) DEFAULT '1' COMMENT '状态(1=有效；0=无效）',
  `create_user` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `sys_other_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_other_config`;
CREATE TABLE `sys_other_config` (
  `id` varchar(50) NOT NULL,
  `other_name` varchar(200) DEFAULT NULL COMMENT '三方名称',
  `config_column` longtext COMMENT '三方配置字段',
  `create_user` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` smallint(2) DEFAULT '1',
  `type` smallint(2) DEFAULT '1' COMMENT '参数类型(1=三方参数;0=系统配置参数)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='三方列表';


-- ----------------------------
-- Table structure for `sys_para`
-- ----------------------------
DROP TABLE IF EXISTS `sys_para`;
CREATE TABLE `sys_para` (
  `para_name` varchar(200) NOT NULL COMMENT '参数名称',
  `para_value` text COMMENT '参数值',
  `remark` varchar(400) DEFAULT NULL COMMENT '参数描述',
  `company_code` varchar(50) NOT NULL COMMENT '所属公司',
  `other_id` varchar(50) NOT NULL COMMENT '三方id',
  `conf_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`para_name`,`company_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统参数表';

-- ----------------------------
-- Records of sys_para
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_province`
-- ----------------------------
DROP TABLE IF EXISTS `sys_province`;
CREATE TABLE `sys_province` (
  `id` varchar(40) NOT NULL COMMENT '唯一标识 (省份表)',
  `create_time` varchar(40) DEFAULT NULL COMMENT '创建时间',
  `province_name` varchar(100) DEFAULT NULL COMMENT '省份名称',
  `update_time` varchar(40) DEFAULT NULL COMMENT '更新时间',
  `short_name` varchar(40) DEFAULT NULL COMMENT '省份简称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `sys_sms`
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms`;
CREATE TABLE `sys_sms` (
  `id` varchar(128) NOT NULL COMMENT '编号',
  `type` varchar(30) NOT NULL COMMENT '短信类型0 系统 1交易 2推送消息 3注册',
  `tel` varchar(30) NOT NULL COMMENT '接收的手机号',
  `var_code` varchar(20) NOT NULL COMMENT '验证码',
  `create_time` varchar(20) NOT NULL COMMENT '发送时间',
  `alter_time` varchar(20) DEFAULT NULL,
  `state` varchar(1) DEFAULT '0' COMMENT '0 正常 1 失效',
  `apex1` varchar(30) DEFAULT NULL,
  `apex2` varchar(30) DEFAULT NULL,
  `apex3` varchar(30) DEFAULT NULL,
  `bak` varchar(30) DEFAULT NULL,
  UNIQUE KEY `app_sms_id` (`id`) USING BTREE,
  KEY `app_sms_tel` (`type`,`tel`) USING BTREE,
  KEY `app_sms_cerate_time` (`create_time`) USING BTREE,
  KEY `app_sms_state` (`state`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信验证码存储表';

-- ----------------------------
-- Records of sys_sms
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(50) NOT NULL COMMENT '用户编号',
  `org_id` varchar(50) DEFAULT NULL COMMENT '组织编号',
  `employee_id` varchar(50) DEFAULT NULL COMMENT '员工编号',
  `account` varchar(255) NOT NULL COMMENT '登录账号',
  `password` varchar(255) DEFAULT NULL COMMENT '登录密码',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `sex` smallint(6) DEFAULT NULL COMMENT '性别:1男，２女',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `tel` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `qq` varchar(30) DEFAULT NULL COMMENT 'QQ',
  `status` smallint(6) DEFAULT NULL COMMENT '状态  1：启用 0：停用2：锁定（在密码错误达到一定次数时，进行锁定）',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `role_id` varchar(50) DEFAULT NULL COMMENT '角色id',
  `lock_time` datetime DEFAULT NULL COMMENT '锁定时间',
  `latest_time` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `latest_ip` varchar(25) DEFAULT NULL COMMENT '最后一次登录IP',
  `error_count` int(11) DEFAULT NULL COMMENT '错误次数（通过手动解锁来重置错误次数）',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_USER` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户管理表';


-- ----------------------------
-- Table structure for `sys_web_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_web_log`;
CREATE TABLE `sys_web_log` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `login_account` varchar(64) DEFAULT NULL COMMENT '登录账号',
  `company_code` varchar(64) DEFAULT NULL COMMENT '企业编码',
  `company_name` varchar(64) DEFAULT NULL COMMENT '企业名称',
  `method` varchar(64) DEFAULT NULL COMMENT '方法名',
  `method_desc` varchar(100) DEFAULT NULL COMMENT '方法描述',
  `method_args` longtext COMMENT '方法参数',
  `operate_time` datetime DEFAULT NULL COMMENT '登录时间',
  `operate_ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `operate_type` varchar(4) DEFAULT NULL COMMENT '操作类型（0-登录日志，1-操作日志）',
  `terminal_type` varchar(4) DEFAULT NULL COMMENT '终端(0-pc端，1-安卓，2-ios)',
  `status` varchar(4) DEFAULT NULL COMMENT '状态(0成功1异常)',
  `status_code` varchar(4) DEFAULT NULL COMMENT '状态码',
  `messages` varchar(100) DEFAULT NULL COMMENT '请求返回描述',
  `is_delete` int(1) DEFAULT NULL COMMENT '是否删除',
  `exception_code` varchar(64) DEFAULT NULL COMMENT '异常编码',
  `exception_description` varchar(64) DEFAULT NULL COMMENT '异常描述',
  `exception_stack_msg` longtext COMMENT '异常堆栈',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志表';

-- ----------------------------
-- Records of sys_web_log
-- ----------------------------
DROP TRIGGER IF EXISTS `update_alter_time_sms`;
DELIMITER ;;
CREATE TRIGGER `update_alter_time_sms` BEFORE UPDATE ON `sys_sms` FOR EACH ROW begin
 set new.alter_time = DATE_FORMAT(NOW(),'%Y%m%d%H%i%s');
end
;;
DELIMITER ;
