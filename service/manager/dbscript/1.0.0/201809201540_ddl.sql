ALTER TABLE `ent_contract_book_detail`
ADD COLUMN `order_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单表id' AFTER `product_id`,
ADD COLUMN `contract_start_date`  date NULL DEFAULT NULL COMMENT '合同开始开效时间' AFTER `order_id`,
ADD COLUMN `contract_end_date`  date NULL DEFAULT NULL COMMENT '合同生效截止时间' AFTER `contract_start_date`;


ALTER TABLE `ent_goods`
MODIFY COLUMN `point_amount`  int(11) NULL DEFAULT NULL COMMENT '积分兑换数量' AFTER `is_point`;

INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES ('b7a11ce7-2567-4c3b-b24a-cec653fe4bfb', 'XFJR201809130002', 'penaltyRule', '4', '借款本金', '1', '', '管理员', '2018-09-25 13:14:50', NULL, NULL);
