package cn.fintecher.system.service;

import java.util.List;
import java.util.Map;

public interface FinancialService {
    //还款列表
    Map getRepaymentRecordList(Map map);

    List getRepaymentDetail(String repaymentRecordId);
}
