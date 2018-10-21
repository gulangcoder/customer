package cn.fintecher.app.service.bank;

import cn.fintecher.app.model.bank.EntCustBank;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月20日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
public interface CustBankService {

    /**
     * 绑卡
     * @param map
     * @return
     * @throws Exception
     */
    Map bindingCard(Map<String,String> map) throws Exception;


    /**
     * 绑卡重发接口
     * @param map
     * @return
     * @throws Exception
     */
    Map bindingCardResend(Map<String,String> map)throws Exception;
    /**
     * 确认绑卡接口
     * @param map
     * @return
     * @throws Exception
     */
    Map bindingCardConfirm (Map<String,String> map) throws Exception;



    List<EntCustBank> getCustBankList(String custId)throws Exception;
}
