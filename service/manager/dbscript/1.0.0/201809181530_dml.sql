delete from `zw_fin`.`sys_dict_item` where item_code ='msgType';
INSERT INTO `zw_fin`.`sys_dict_item` (`id`, `company_code`, `item_code`, `item_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', '短信模板类型', '1', '', 'admin', '2018-09-06 13:59:22', 'admin', '2018-09-07 13:31:42');

delete from `zw_fin`.`sys_dict_detail` where item_code ='msgType';

INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'registerSucc', '注册成功', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'singleLogin', '单点登录', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'updatePsdSucc', '修改密码提醒', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'coupon', '优惠券提醒', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'tieCard', '绑定银行卡', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'applyQuotaSucc', '额度申请成功', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'applyQuotaFail', '额度申请失败', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'frozenQuota', '额度冻结提醒', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'soonOverdueQuota', '额度即将过期', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'overdueQuota', '额度过期', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'loanSucc', '借款成功', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'loanFail', '借款失败', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'repayThree', '还款前三天提醒', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'repayOne', '还款前一天提醒', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'repayDay', '还款日提醒', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'withdrawSucc', '系统扣款成功', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'withdrawFail', '系统扣款失败', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'repaySucc', '还款成功', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'beOverdueOne', '逾期后一天提醒', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'beOverdueTwo', '逾期后两天提醒', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');
INSERT INTO `zw_fin`.`sys_dict_detail` (`id`, `company_code`, `item_code`, `detail_code`, `detail_name`, `status`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (UUID(), 'XFJR201809130002', 'msgType', 'beOverdueThree', '逾期后三天提醒', '1', '', '管理员', '2018-09-13 16:50:08', '管理员', '2018-09-13 16:50:37');



delete from `zw_fin`.`ent_message_templet`;


INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '短信验证码', 'register', '亲爱的用户，您的短信验证码为${code},${number}分钟内有效，若非本人操作请忽略。', '0', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '注册成功', 'registerSucc', '注册成功：恭喜您成为XX会员，我们将竭诚为您服务！', '1', '1', '0', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '单点登录', 'singleLogin', '您的账号已在其他设备登录，请确认是否本人操作。', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '修改密码提醒', 'updatePsdSucc', '修改密码提醒：修改密码成功，请妥善保管！', '1', '1', '0', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '优惠券提醒', 'coupon', '优惠券提醒：您有新的优惠券到账，请查收！', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '绑定银行卡', 'tieCard', '绑定银行卡：您的尾号为${last_number}银行卡已绑定。', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '额度申请成功', 'applyQuotaSucc', '您的额度申请已通过审批，您的总授信额度仍为${money}元，请及时使用，超时额度将会失效，需重新申请才能获取额度，请您知悉。', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '额度申请失败', 'applyQuotaFail', '额度申请失败：很抱歉地通知您，因综合信用欠佳，您提交的额度申请没有通过审批，请您保持良好的信用习惯。', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '额度冻结提醒', 'frozenQuota', '额度冻结提醒：您的额度已被冻结，请您知悉。', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '额度即将过期', 'soonOverdueQuota', '额度即将过期：您的额度将在三天后失效，额度失效后需再次申请才能获得额度，请您知悉。', '1', '1', '0', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '额度过期', 'overdueQuota', '额度过期：您的额度已过期失效，立即申请，获取最新额度！', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '借款成功', 'loanSucc', '借款成功：恭喜您！您的借款请求已通过审批，款项已打到您绑定的卡内，请您留意。', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '借款失败', 'loanFail', '借款失败：很抱歉地通知您，您提交的借款申请没有通过审批，请您保持良好的还款记录。', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '还款前三天提醒', 'repayThree', '尊敬的客户，诚挚提醒您${month}月${day}日是您的还款日，为避免影响您的个人信用记录，请及时还款，如已足额还款请忽略此提醒。', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '还款前一天提醒', 'repayOne', '尊敬的客户，诚挚提醒您${month}月${day}日是您的还款日，为避免影响您的个人信用记录，请及时还款，如已足额还款请忽略此提醒。', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '还款日提醒', 'repayDay', '尊敬的客户，诚挚提醒您今天是您的还款日，为避免影响您的个人信用记录，请及时还款，如已足额还款请忽略此提醒。', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '系统扣款成功', 'withdrawSucc', '系统扣款成功：尊敬的客户，已通过您绑定的银行卡账户自动扣款成功，扣款金额为${money}元，请您知悉。', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '系统扣款失败', 'withdrawFail', '系统扣款失败：尊敬的客户，本次通过您绑定的银行卡账户自动扣款失败，因账户余额不足导致的扣款失败将影响您的个人征信记录，请您保证您的银行卡余额充足，或进入XX APP进行手动还款。', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '还款成功', 'repaySucc', '还款成功：尊敬的客户，本次还款成功，扣款金额为${money}元，请您知悉。', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '逾期后一天提醒', 'beOverdueOne', '逾期后一天提醒：尊敬的 ${user_name} 先生 /女士,您好!截止到 ${year} 年 ${month} 月 ${day} 日,你的 ${periods_number} 期款至今未还,请及时还款,否则将影响到你的信用征信,咨询热线 XXXXXXXX !', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '逾期后两天提醒', 'beOverdueTwo', '逾期后两天提醒：尊敬的 ${user_name} 先生 /女士,您好!截止到 ${year} 年 ${month} 月 ${day} 日,你的 ${periods_number} ', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');
INSERT INTO `zw_fin`.`ent_message_templet` (`id`, `title`, `msg_type`, `content`, `is_push`, `is_private_msg`, `Is_send_msg`, `is_access`, `company_code`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) VALUES (UUID(), '逾期后三天提醒', 'beOverdueThree', '逾期后三天提醒；尊敬的 ${user_name} 先生 /女士,您好!截止到 ${year} 年 ${month} 月 ${day} 日,你的 ${periods_number} 期款至今未还,请及时还款,否则将影响到你的信用征信,咨询热线 XXXXXXXX !', '1', '1', '1', '0', 'XFJR201809130002', 'admin', '2018-09-17 20:01:39', NULL, NULL, '1');