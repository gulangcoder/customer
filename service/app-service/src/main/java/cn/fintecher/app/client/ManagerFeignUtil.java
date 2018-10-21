package cn.fintecher.app.client;

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

    public static final String SYSTEM_API_REPAYMENTPLAN_GENREPAYMENTPLAN_API="http://system-web/api/repaymentPlan/genRepaymentPlan";
}
