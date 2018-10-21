package cn.fintecher.app.service.order.impl;

import cn.fintecher.app.mapper.order.EntRepaymentPlanMapper;
import cn.fintecher.app.mapper.order.EntRepaymentRecordDetailedMapper;
import cn.fintecher.app.mapper.order.EntRepaymentRecordMapper;
import cn.fintecher.app.model.order.EntRepaymentPlan;
import cn.fintecher.app.model.order.EntRepaymentRecord;
import cn.fintecher.app.model.order.EntRepaymentRecordDetailed;
import cn.fintecher.app.service.order.RepaymentTableService;
import cn.fintecher.util.ChkUtil;
import cn.fintecher.util.SensitiveInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class RepaymentTableServiceImpl implements RepaymentTableService {

    @Autowired
    private EntRepaymentPlanMapper entRepaymentPlanMapper;
    @Autowired
    private EntRepaymentRecordMapper entRepaymentRecordMapper;
    @Autowired
    private EntRepaymentRecordDetailedMapper entRepaymentRecordDetailedMapper;
    @Override
    public boolean addRepaymentPlan(EntRepaymentPlan entRepaymentPlan) throws Exception {
        return false;
    }

    @Override
    public boolean updateRepaymentPlan(EntRepaymentPlan entRepaymentPlan) throws Exception {
        return false;
    }

    @Override
    public boolean delRepaymentPlan(EntRepaymentPlan entRepaymentPlan) throws Exception {
        return false;
    }

    @Override
    public EntRepaymentPlan getRepaymentPlan(EntRepaymentPlan entRepaymentPlan) throws Exception {
        return null;
    }

    @Override
    public List<EntRepaymentPlan> getRepaymentPlanList(EntRepaymentPlan entRepaymentPlan) throws Exception {
        return entRepaymentPlanMapper.getList(entRepaymentPlan);
    }

    @Override
    public boolean addRepaymentRecord(EntRepaymentRecord repaymentRecord) throws Exception {
        return false;
    }

    @Override
    public boolean updateRepaymentRecord(EntRepaymentRecord repaymentRecord) throws Exception {
        return false;
    }

    @Override
    public boolean delRepaymentRecord(EntRepaymentRecord repaymentRecord) throws Exception {
        return false;
    }

    @Override
    public EntRepaymentRecord getRepaymentRecord(EntRepaymentRecord repaymentRecord) throws Exception {
        return null;
    }

    @Override
    public List<EntRepaymentRecord> getRepaymentRecordList(EntRepaymentRecord repaymentRecord) throws Exception {
        return null;
    }

    @Override
    public boolean addEntRepaymentRecordDetailed(EntRepaymentRecordDetailed repaymentRecordDetailed) throws Exception {
        return false;
    }

    @Override
    public List<EntRepaymentRecordDetailed> getEntRepaymentRecordDetailedList(EntRepaymentRecordDetailed repaymentRecordDetailed) throws Exception {
        return null;
    }

    @Override
    public Map getOverdueCount(String orderIds) {
        return entRepaymentPlanMapper.getOverdueCount(orderIds);
    }

    @Override
    public List<Map> getRepaymentRecordList(Map paramMap) {
        List<Map> mapList = entRepaymentRecordMapper.getRepaymentRecordList(paramMap);
        for (Map map : mapList) {
            //数据脱敏
            map.put("realName", SensitiveInfoUtils.chineseName((String)map.get("realName")));
            map.put("idcardNum",SensitiveInfoUtils.idCardNum((String)map.get("idcardNum")));
            String recordId = map.get("id").toString();
            //一对多
            EntRepaymentRecordDetailed repaymentRecordDetailed = new EntRepaymentRecordDetailed();
            repaymentRecordDetailed.setRepaymentRecordId(recordId);
            List<Map> list = entRepaymentRecordDetailedMapper.getPaidList(repaymentRecordDetailed);
            //listtoString
            BigDecimal shouldTotal = BigDecimal.ZERO;
            if (ChkUtil.isNotEmpty(list)){
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (ChkUtil.isNotEmpty((BigDecimal) list.get(i).get("principal"))){
                        shouldTotal  = shouldTotal.add((BigDecimal) list.get(i).get("principal"));
                    }
                    if (ChkUtil.isNotEmpty((BigDecimal) list.get(i).get("interest"))){
                        shouldTotal  = shouldTotal.add((BigDecimal) list.get(i).get("interest"));
                    }
                    if (ChkUtil.isNotEmpty((BigDecimal) list.get(i).get("service_charge"))){
                        shouldTotal  = shouldTotal.add((BigDecimal) list.get(i).get("service_charge"));
                    }
                    if (ChkUtil.isNotEmpty((BigDecimal) list.get(i).get("default_interest"))){
                        shouldTotal  = shouldTotal.add((BigDecimal) list.get(i).get("default_interest"));
                    }
                    if (ChkUtil.isNotEmpty((BigDecimal) list.get(i).get("penalty"))){
                        shouldTotal  = shouldTotal.add((BigDecimal) list.get(i).get("penalty"));
                    }

                    if (i == list.size() - 1) {
                        sb.append(list.get(i).get("periods_number").toString());
                    } else {
                        sb.append(list.get(i).get("periods_number").toString());
                        sb.append(",");
                    }
                }
                map.put("shouldTotal", shouldTotal);
                map.put("paidPeriodsNumbers",sb);
            }
        }
        return mapList;
    }

    @Override
    public List getRepaymentDetail(String repaymentRecordId) {
        return entRepaymentRecordDetailedMapper.getRepaymentDetail(repaymentRecordId);
    }
}
