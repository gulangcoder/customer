package cn.fintecher.system.client;

import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ComponentFeignClient
 * @Description
 * @Author coder_bao
 * @Date 2018/8/23 10:36
 */
@FeignClient("manager")
public interface ManagerFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/syslogin/queryUserPermission")
    ResponseEntity<List<String>> queryUserPermission(@RequestParam(value = "userId") @ApiParam("用户主键") String userId);

    /**
     * 查询日志列表
     * @param pageSize
     * @param pageIndex
     * @param loginAccount
     * @param companyCode
     * @param method
     * @param terminalType
     * @param operateType
     * @param status
     * @param exceptionCode
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "http://manager/api/weblog//getSysWebLogList")
    ResponseEntity getSysWebLogList(@RequestParam(value = "pageSize",required = false) Integer pageSize,
                                           @RequestParam(value = "pageIndex",required = false) Integer pageIndex,
                                           @RequestParam(value = "loginAccount",required = false) String loginAccount,
                                           @RequestParam(value = "companyCode",required = false) String companyCode,
                                           @RequestParam(value = "method",required = false) String method,
                                           @RequestParam(value = "terminalType",required = false) String terminalType,
                                           @RequestParam(value = "operateType",required = false) String operateType,
                                           @RequestParam(value = "status",required = false) String status,
                                           @RequestParam(value = "exceptionCode",required = false) String exceptionCode,
                                           @RequestParam(value = "startTime",required = false) String startTime,
                                           @RequestParam(value = "endTime",required = false) String endTime);


    /**
     * 根据id查询日志错误信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "http://manager/api/weblog/getExceptionStackMsgById")
    ResponseEntity getExceptionStackMsgById(@RequestParam(value = "id") String id);


    /**
     * 签订合同上传
     * @param data
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "http://manager/api/contractUpload/upload")
    ResponseEntity<Map> upload(@RequestBody Map data);


    /**
     * 发送短信接口
     * @param data
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "http://manager/api/sms/sendMessage")
    ResponseEntity sendMessage(@RequestBody Map data);


    /**
     * 极光推送接口
     * @param data
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "http://manager/api/push/jiguangPush")
    ResponseEntity jiguangPush(@RequestBody Map data);

    @RequestMapping(method = RequestMethod.POST,value = "http://app-service/customer/customerList")
    ResponseEntity getCustomerList(@RequestBody Map data);

    //获取客户额度信息
    @RequestMapping(method = RequestMethod.POST,value = "http://app-service/api/quota/getCustQuota")
    ResponseEntity getQuota(@RequestBody String custId);
    //授信列表
    @RequestMapping(method = RequestMethod.POST,value = "http://app-service/api/quota/getCreditList")
    ResponseEntity getCreditList(@RequestBody Map map);

    /**
     * 借款管理订单列表
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "http://app-service/api/order/getQuota")
    ResponseEntity getOrdersInfo(@RequestBody Map map);
    /**
     * 根据订单id获取还款计划表集合
     * @param orderId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "http://app-service/api/order/getRepaymentPlanList")
    ResponseEntity getRepaymentPlanList(@RequestBody String orderId);
    /**
     * 放款信息
     * @param orderId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "http://app-service/api/repayment/loanInfo")
    ResponseEntity getLoanInfo(@RequestParam("orderId") String orderId, @RequestParam("custId") String custId);
    /**
     * 根据订单id获取还款计划第一期
     * @param orderId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "http://app-service/api/repayment/getRepaymentPlanFirst")
    ResponseEntity getRepaymentPlanFirst(@RequestBody String orderId);
    /**
     * 根据订单id获取还款计划第一期
     * @param orderId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "http://app-service/api/order/getQuotaList")
    ResponseEntity quotaList(@RequestBody String orderId);
    /**
     * 还款列表
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "http://app-service/api/repayment/getRepaymentRecordList")
    ResponseEntity getRepaymentRecordList(@RequestBody Map map);
    /**
     * 还款详情
     * @param repaymentRecordId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "http://app-service/api/repayment/getRepaymentDetail")
    ResponseEntity getRepaymentDetail(@RequestBody String repaymentRecordId);

}
