package cn.fintecher.app.client;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/19
 * @Description:
 */
public class AppFeginUtil {

    //注册通知调用服务api
    public static final String SYSTEMWEB_API_SAVEMESTEMPLDETAIL_API="http://system-web/api/entMsgTempl/saveMesTemplDetail";
    //短信调用服务api
    public static final String MANAGER_API_SENDSMSCODE_API="http://manager/api/sms/sendMessage";
    //查询短信代理商服务api
    public static final String SYSTEMWEB_API_QUERYClACCOUNT_API="http://system-web/api/sysOtherConfig/queryClAccount";
    //获取相关协议
    public static final String SYSTEMWEB_API_CONTRACT_API="http://system-web/api/entContractTempl/getProtocol";
    //短信验证码模板调用服务api
    public static final String SYSTEMWEB_API_SMSCODETEMPLATE_API="http://system-web/api/entMsgTempl/queryMesTemplContent";
    //产品影像资料api
    public static final String SYSTEMWEB_API_PRODUCTVIDEO_API="http://system-web/api/product/getProductById";
}
