CREATE TABLE `ent_order` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `company_code` varchar(64) DEFAULT NULL COMMENT '公司编码',
  `order_no` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `cust_id` varchar(64) DEFAULT NULL COMMENT '用户详情id',
  `state` int(11) DEFAULT NULL COMMENT '1借款申请;2放款审批;3放款中;4已放款;5结清;6拒绝;7放款失败',
  `product_credit_id` varchar(64) DEFAULT NULL COMMENT '产品信贷费率',
  `loan_amount` decimal(20,2) DEFAULT '0.00' COMMENT '借款金额',
  `grant_flow_number` varchar(64) DEFAULT NULL COMMENT '放款流水号',
  `loan_purpose` varchar(64) DEFAULT NULL COMMENT '借款用途',
  `grant_time` datetime DEFAULT NULL COMMENT '放款时间',
  `expiry_time` datetime DEFAULT NULL COMMENT '到期时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT 'update_time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';


CREATE TABLE `ent_repayment_batch` (
  `id` varchar(64) NOT NULL,
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单id',
  `periods_number` int(11) DEFAULT NULL COMMENT '当前期数',
  `principal` decimal(20,2) DEFAULT '0.00' COMMENT '本金',
  `interest` decimal(20,2) DEFAULT '0.00' COMMENT '利息',
  `start_time` datetime DEFAULT NULL,
  `expiry_time` datetime DEFAULT NULL COMMENT '到期时间',
  `service_charge` decimal(20,2) DEFAULT '0.00' COMMENT '服务费',
  `preposition_service_charge` decimal(20,2) DEFAULT '0.00' COMMENT '前置服务费',
  `bond` decimal(20,2) DEFAULT '0.00' COMMENT '保证金',
  `default_interest` decimal(20,2) DEFAULT '0.00' COMMENT '罚息',
  `penalty` decimal(20,2) DEFAULT '0.00' COMMENT '违约金',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='还款计划跑批表';




CREATE TABLE `ent_repayment_plan` (
  `id` varchar(64) NOT NULL,
  `cust_id` varchar(64) DEFAULT NULL COMMENT '客户详情id',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单id',
  `periods_number` int(11) DEFAULT NULL COMMENT '当前期数',
  `principal` decimal(20,2) DEFAULT '0.00' COMMENT '本金',
  `interest` decimal(20,2) DEFAULT '0.00',
  `start_time` varchar(64) DEFAULT NULL COMMENT '开始时间',
  `expiry_time` varchar(64) DEFAULT NULL COMMENT '到期时间',
  `service_charge` decimal(20,2) DEFAULT '0.00' COMMENT '服务费',
  `preposition_service_charge` decimal(20,2) DEFAULT '0.00' COMMENT '前置服务费',
  `poundage` decimal(20,2) DEFAULT '0.00' COMMENT '转账手续费',
  `bond` decimal(20,2) DEFAULT '0.00' COMMENT '保证金',
  `state` int(11) DEFAULT NULL COMMENT '状态0待还1还款中2已还款',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='还款计划表';




CREATE TABLE `ent_repayment_record` (
  `id` varchar(64) NOT NULL COMMENT '主键同时也是第三请求流水号',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单id',
  `periods_number` int(11) DEFAULT NULL COMMENT '当前期数',
  `principal` decimal(20,2) DEFAULT '0.00' COMMENT '本金',
  `interest` decimal(20,2) DEFAULT '0.00',
  `service_charge` decimal(20,2) DEFAULT '0.00' COMMENT '服务费',
  `default_interest` decimal(20,2) DEFAULT '0.00' COMMENT '罚息',
  `penalty` decimal(20,2) DEFAULT '0.00' COMMENT '违约金',
  `total` decimal(20,2) DEFAULT '0.00' COMMENT '还款总金额',
  `state` int(11) DEFAULT '0' COMMENT '状态：0还款中，1还款完成，2还款失败',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `repayment_type` int(11) DEFAULT '0' COMMENT '还款类型:0手动，1系统跑批',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='还款流水表';


CREATE TABLE `ent_repayment_record_detailed` (
  `id` varchar(64) NOT NULL COMMENT '还款记录id',
  `repayment_record_id` varchar(64) DEFAULT NULL,
  `repayment_plan_id` varchar(64) DEFAULT NULL COMMENT '还款计划表id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='还款流水明细表';
