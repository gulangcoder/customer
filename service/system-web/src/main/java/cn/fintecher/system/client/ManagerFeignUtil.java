package cn.fintecher.system.client;

/**
 * @ClassName ManagerFeignUtil
 * @Description 配置Feign服务被调用方的api地址
 * @Author coder_bao
 * @Date 2018/8/23 15:15
 */
public class ManagerFeignUtil {
    //测试RestTemplate方式调用服务api
    public static final String MANAGER_API_SYSLOGIN_QUERYUSERPERMISSION_API="http://manager-service/api/syslogin/queryUserPermission";
    public static final String MANAGER_API_SMS_SENDMESSAGE_API="http://manager/api/sms/sendMessage";
    public static final String MANAGER_API_PUSH_JIGUANGPUSH_API="http://manager/api/push/jiguangPush";

    public static final String MANAGER_API_CONTRACTUPLOAD_API="http://manager/api/contractUpload";

    public static final String MANAGER_API_SYSWEBLOG_GETLOGLIST_API="http://manager/api/weblog/getSysWebLogList";

    public static final String MANAGER_API_SYSWEBLOG_GETERRORMSG_API="http://manager/api/weblog/getExceptionStackMsgById";

    //客户列表
    public static final String APP_SERVICE_API_CUSTOMER_LIST_API="http://app-service/api/customer/customerList";
    //客户详情
    public static final String APP_SERVICE_API_CUSTOMER_DETAIL_API="http://app-service/api/customer/getCustomerDetail";
    //客户授信信息
    public static final String APP_SERVICE_API_CREDIT_DETAIL_API="http://app-service/api/customer/creditDetail";
    //更新用户主表信息
    public static final String APP_SERVICE_API_UPDATE_DETAIL_API="http://app-service/api/customer/updateCustomer";

    //借款管理列表
    public static final String APP_SERVICE_API_GET_ORDERS_INFO_API="http://app-service/api/order/getBorrowOrders";
    //根据订单id获取还款计划表集合
    public static final String APP_SERVICE_API_REPAYMENTPLAN_LIST_API="http://app-service/api/repayment/getRepaymentPlanList";
    //放款信息
    public static final String APP_SERVICE_API_LOAN_INFO_API="http://app-service/api/repayment/loanInfo";
    //还款计划第一期
    public static final String APP_SERVICE_API_REPAYMENT_PLAN_FIRST_API="http://app-service/api/repayment/getRepaymentPlanFirst";
    //还款列表
    public static final String APP_SERVICE_API_REPAYMENT_RECORD_LIST_API="http://app-service/api/repayment/getRepaymentRecordList";
    //还款详情
    public static final String APP_SERVICE_API_REPAYMENT_DETAIL_API="http://app-service/api/repayment/getRepaymentDetail";
    //获取客户额度信息
    public static final String APP_SERVICE_API_GET_QUOPTA_API="http://app-service/api/quota/getCustQuota";
    //查询额度申请历史
    public static final String APP_SERVICE_API_GET_QUOPTA_LIST_API="http://app-service/api/quota/getQuotaList";
    //授信列表
    public static final String APP_SERVICE_API_GET_CREDITLIST_API="http://app-service/api/quota/getCreditList";
}
