DROP TABLE IF EXISTS `ent_cust_quota`;
CREATE  TABLE `ent_cust_quota` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `cust_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `initial_amount` decimal(20,2) DEFAULT NULL COMMENT '初始额度',
  `total_amount` decimal(20,2) DEFAULT NULL COMMENT '额度总额',
  `available_amount` decimal(20,2) DEFAULT NULL COMMENT '可用额度',
  `product_id` varchar(64) DEFAULT NULL COMMENT '产品id',
  `state` int(11) DEFAULT NULL COMMENT '额度状态：0提交审核中，1正常，2产品下架，3额度失效',
  `credit_send_time` datetime DEFAULT NULL COMMENT '授信发起时间',
  `credit_return_time` datetime DEFAULT NULL COMMENT '授信返回时间',
  `credit_result` int(11) DEFAULT NULL COMMENT '授信结果（1通过，2拒绝）',
  `refuse_code` varchar(30) DEFAULT NULL COMMENT '拒绝code',
  `effective_time` datetime DEFAULT NULL COMMENT '有效期',
  `enable_time` datetime DEFAULT NULL COMMENT '启用时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='额度表';



DROP TABLE IF EXISTS `ent_order`;
CREATE TABLE `ent_order` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `order_no` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `cust_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `state` int(11) DEFAULT NULL COMMENT '1借款申请;2放款审批;3放款中;4已放款;5结清;6拒绝',
  `product_credit_id` varchar(64) DEFAULT NULL COMMENT '产品信贷费率',
  `loan_amount` decimal(20,2) DEFAULT NULL COMMENT '借款金额',
  `grant_flow_number` varchar(64) DEFAULT NULL COMMENT '放款流水号',
  `grant_time` datetime DEFAULT NULL COMMENT '放款时间',
  `expiry_time` datetime DEFAULT NULL COMMENT '到期时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT 'update_time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';


DROP TABLE IF EXISTS `ent_repayment_plan`;
CREATE TABLE `ent_repayment_plan` (
  `id` varchar(64) NOT NULL,
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单id',
  `periods_number` int(11) DEFAULT NULL COMMENT '当前期数',
  `principal` decimal(20,2) DEFAULT NULL COMMENT '本金',
  `interest` decimal(20,2) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `expiry_time` datetime DEFAULT NULL COMMENT '到期时间',
  `service_charge` decimal(20,2) DEFAULT NULL,
  `preposition_service_charge` decimal(20,2) DEFAULT NULL COMMENT '前置服务费',
  `bond` decimal(10,0) DEFAULT NULL COMMENT '保证金',
  `state` int(11) DEFAULT NULL COMMENT '状态0待还1还款中2已还款',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='还款计划表';

DROP TABLE IF EXISTS `ent_repayment_record`;
CREATE TABLE `ent_repayment_record` (
  `id` varchar(64) NOT NULL,
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单id',
  `periods_number` int(11) DEFAULT NULL COMMENT '当前期数',
  `principal` decimal(20,2) DEFAULT NULL COMMENT '本金',
  `interest` decimal(20,2) DEFAULT NULL,
  `service_charge` decimal(20,2) DEFAULT NULL COMMENT '服务费',
  `default_interest` decimal(20,2) DEFAULT NULL COMMENT '罚息',
  `penalty` decimal(20,0) DEFAULT NULL COMMENT '违约金',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='还款流水表';

DROP TABLE IF EXISTS `ent_repayment_record_detailed`;
CREATE TABLE `ent_repayment_record_detailed` (
  `id` varchar(64) NOT NULL COMMENT '还款记录id',
  `repayment_record_id` varchar(64) DEFAULT NULL,
  `repayment_plan_id` varchar(64) DEFAULT NULL COMMENT '还款计划表id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='还款流水明细表';

DROP TABLE IF EXISTS `ent_repayment_batch`;
CREATE TABLE `ent_repayment_batch` (
  `id` varchar(64) NOT NULL,
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单id',
  `periods_number` int(11) DEFAULT NULL COMMENT '当前期数',
  `principal` decimal(20,2) DEFAULT NULL COMMENT '本金',
  `interest` decimal(20,2) DEFAULT NULL COMMENT '利息',
  `start_time` datetime DEFAULT NULL,
  `expiry_time` datetime DEFAULT NULL COMMENT '到期时间',
  `service_charge` decimal(20,2) DEFAULT NULL COMMENT '服务费',
  `preposition_service_charge` decimal(20,2) DEFAULT NULL COMMENT '前置服务费',
  `bond` decimal(20,2) DEFAULT NULL COMMENT '保证金',
  `default_interest` decimal(20,2) DEFAULT NULL COMMENT '罚息',
  `penalty` decimal(20,2) DEFAULT NULL COMMENT '违约金',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='还款计划跑批表';
