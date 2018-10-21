package cn.fintecher.util.yibao;

/**
 * Title :
 * Description : @易宝结果返回信息常量类@
 * Create on : 2018年07月10日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:zhangtao
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
public class RespMsgConstans {

    public static final String WITHDRAW_UNCERTIFIED = "未进行银行卡认证,无法进行提现操作";

    public static final String WITHDRAW_REFUSE = "您当前已有一笔提现操作,无法再次提现";

    public static final String WITHDRAW_SUCCESS = "提现成功";

    public static final String WITHDRAW_WAIT = "您的提现申请已提交成功,请耐心等待银行处理结果";

    public static final String WITHDRAW_FAIL = "您的提现申请失败,请重新发起";

    public static final String WITHDRAW_UNKNOWN = "未知错误";

    public static final String WITHDRAW_AUDIT = "提现申请发起成功,需商户登录易宝商户后台进行审核";

    public static final String WITHDRAW_UNTREATED = "提现申请发起成功,但尚未发到银行";

}
