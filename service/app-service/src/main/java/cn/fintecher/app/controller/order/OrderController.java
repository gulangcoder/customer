package cn.fintecher.app.controller.order;

import cn.fintecher.app.model.order.EntOrder;
import cn.fintecher.app.service.order.OrderService;
import cn.fintecher.app.service.order.QuotaService;
import cn.fintecher.app.service.order.RepaymentTableService;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.util.HeaderUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@Api(value = "订单管理", description = "订单管理")
public class OrderController {
    private static final String ENTITY_NAME = "entOrder";

    @Autowired
    private OrderService orderService;
    @Autowired
    private RepaymentTableService repaymentTableService;
    @Autowired
    private QuotaService quotaService;

    @PostMapping("/getOrderSumByCustId")
    @ApiOperation(value = "根据客户id查询借款总金额,总笔数",httpMethod = "POST", notes = "根据客户id查询借款总金额,总笔数")
    public ResponseEntity getOrderSumByCustId(@RequestBody String customerId){
        LoggerCommon.info(this.getClass(),"根据客户id查询借款总金额,总笔数");
        Map map = null;
        try {
            map = orderService.getOrderSumByCustId(customerId);
        } catch (Exception e) {
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"根据客户id查询借款总金额,总笔数异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"查询成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(map));
    }

    @PostMapping("/getOverdueCount")
    @ApiOperation(value = "根据客户id查询逾期信息统计",httpMethod = "POST", notes = "根据客户id查询逾期信息统计 (总次数,历史最大逾期天数)")
    public ResponseEntity getOverdueCount(@RequestBody String customerId){
        LoggerCommon.info(this.getClass(),"根据客户id查询逾期信息统计");
        Map map = null;
        try {
            map = repaymentTableService.getOverdueCount(customerId);
        } catch (Exception e) {
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"根据客户id查询逾期信息统计异常"+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"查询成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(map));
    }

    @PostMapping("/getBorrowOrders")
    @ApiOperation(value = "借款管理订单列表",httpMethod = "POST", notes = "借款管理订单列表")
    public ResponseEntity getOrdersInfo(@RequestBody Map map){
        LoggerCommon.info(this.getClass(),"借款管理订单列表");
        PageInfo pageInfo = null;
        try {
            int pageIndex = Integer.parseInt(map.get("pageIndex").toString());
            int pageSize = Integer.parseInt(map.get("pageSize").toString());
            PageHelper.startPage(pageIndex, pageSize, true);
            List<Map> orders = orderService.getOrdersInfo(map);
            pageInfo = new PageInfo(orders);
        } catch (Exception e) {
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"异常"+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"查询成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(pageInfo));
    }



    @ApiOperation(value = "客户订单查询", notes = "客户订单查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "state", value = "订单状态'1借款申请;2放款审批;3放款中;4已放款;5结清;6拒绝;7放款失败", dataType = "", paramType = "query", required = false),
    })
    @GetMapping("/getOrders")
    public ResponseEntity getOrders(String orderId,Integer state){
        String logmsg= LocaleMessage.get("message.system.successMessage");
        EntOrder entOrder=new EntOrder();
        entOrder.setId(orderId);
        entOrder.setState(state);
        UserInfo userInfo = UserContextUtil.getUserInfo();
        entOrder.setCustId(userInfo.getUserId());
        entOrder.setCompanyCode(userInfo.getCompanyCode());
        try {
            List<EntOrder> orderList=orderService.getOrderList(entOrder);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(orderList);
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("message.quota.exception");
            LoggerCommon.info(this.getClass(),"客户订单查询："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "客户订单详情", notes = "客户订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/getOrderById")
    public ResponseEntity getOrderById(String orderId){
        String logmsg= LocaleMessage.get("message.system.successMessage");
        if (orderId==null||"".equals(orderId.trim())){
            LocaleMessage.get("message.loan.parameter.failure");
            LoggerCommon.info(this.getClass(),"获取订单详情失败："+logmsg);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
        }
        EntOrder entOrder=new EntOrder();
        entOrder.setId(orderId);
        try {
            EntOrder order=orderService.getOrder(entOrder);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(order);
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("system.server.exception");
            LoggerCommon.info(this.getClass(),"获取订单详情："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

}
