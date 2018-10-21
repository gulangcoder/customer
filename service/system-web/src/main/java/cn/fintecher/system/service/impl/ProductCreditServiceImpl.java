package cn.fintecher.system.service.impl;

import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.mapper.EntProductCompanyRateMapper;
import cn.fintecher.system.mapper.EntProductCreditMapper;
import cn.fintecher.system.mapper.EntProductLadderMapper;
import cn.fintecher.system.model.EntProductCompanyRate;
import cn.fintecher.system.model.EntProductCredit;
import cn.fintecher.system.model.EntProductLadder;
import cn.fintecher.system.service.ProductCreditService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.DateConversionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/6
 * @Description:
 */
@Service
public class ProductCreditServiceImpl implements ProductCreditService {

    @Autowired
    private EntProductCreditMapper entProductCreditMapper;

    @Autowired
    private EntProductLadderMapper entProductLadderMapper;

    @Autowired
    private EntProductCompanyRateMapper entProductCompanyRateMapper;

    @Override
    public List<EntProductCredit> getProductCreditList(Map map) throws Exception{
        List<EntProductCredit> list = entProductCreditMapper.selectCreditListByMap(map);
        for (int i=0;i<list.size();i++){
            List<Map> overdue = new ArrayList<>();
            List<Map> prement = new ArrayList<>();
            Map cMap = new HashMap();
            cMap.put("productCreditId",list.get(i).getId());
            cMap.put("batch",list.get(i).getBatch());
            //阶梯费率
            if ((list.get(i).getOverdueRateType() == 1) || (list.get(i).getHavePrepayment() == 0 && list.get(i).getPrepaymentRateType() == 1)){
                List<EntProductLadder> ladderList = entProductLadderMapper.selectLadderByMap(cMap);
                //list.get(i).setLadderList(ladderList);
                for (int j=0;j<ladderList.size();j++){
                    if (ladderList.get(j).getType() == 0) {
                        Map overdueMap = ladderOfType(ladderList.get(j));
                        overdue.add(overdueMap);
                    }
                    if (ladderList.get(j).getType() == 1) {
                        Map paymentMap = ladderOfType(ladderList.get(j));
                        prement.add(paymentMap);
                    }
                }
                list.get(i).setOverdueLadder(overdue);
                list.get(i).setPrepaymentLadder(prement);
            }
        }
        return list;
    }

    @Transactional
    @Override
    public int addProductCredit(EntProductCredit productCredit) throws Exception{
        int row = entProductCreditMapper.insertSelective(productCredit);
        if (row == 1) {
            // 1 阶梯费率 //添加逾期阶梯费率
            if(productCredit.getOverdueRateType() == 1 && productCredit.getOverdueLadder().size()>0){
                int overNum = addProductLadder(productCredit,productCredit.getOverdueLadder(),(short)0);
                if (overNum != 1){
                    return overNum;
                }
            }
            if (productCredit.getHavePrepayment() == 0) {
                //添加提前还款阶梯费率
                if(productCredit.getPrepaymentRateType() == 1 && productCredit.getPrepaymentLadder().size()>0){
                    int payNum = addProductLadder(productCredit,productCredit.getPrepaymentLadder(),(short)1);
                    if (payNum != 1){
                        return payNum;
                    }
                }
                // 添加提前还款公司占比
                if(productCredit.getPrepaymentCompanyType() == 0 && productCredit.getPrepaymentCompanyRate().size()>0){
                    int pRateNum = addCompanyRate(productCredit,productCredit.getPrepaymentCompanyRate(),(short)1);
                    if (pRateNum == 0){
                        return pRateNum;
                    }
                }
            }
            // 0 需要添加 //添加逾期公司占比
            if(productCredit.getNopromiseCompanyType() == 0 && !"".equals(productCredit.getOverdueCompanyRate())){
                int oRateNum = addCompanyRate(productCredit,productCredit.getOverdueCompanyRate(),(short)0);
                if (oRateNum == 0){
                    return oRateNum;
                }
            }
            // 0 有保证金 服务费 手续费
            if (productCredit.getHaveCash() == 0) {
                // 添加保证金公司占比
                if(productCredit.getCashCompanyType() == 0 && productCredit.getCashCompanyRate().size()>0){
                    int cRateNum = addCompanyRate(productCredit,productCredit.getCashCompanyRate(),(short)2);
                    if (cRateNum == 0){
                        return cRateNum;
                    }
                }
            }
            if (productCredit.getHaveService() == 0) {
                // 添加服务费公司占比
                if(productCredit.getServiceCompanyType() == 0 && productCredit.getServiceCompanyRate().size()>0){
                    int sRateNum = addCompanyRate(productCredit,productCredit.getServiceCompanyRate(),(short)3);
                    if (sRateNum == 0){
                        return sRateNum;
                    }
                }
            }
            if (productCredit.getHavePoundage() == 0) {
                // 添加手续费公司占比
                if(productCredit.getPoundageCompanyType() == 0 && productCredit.getPoundageCompanyRate().size()>0){
                    int pRateNum = addCompanyRate(productCredit,productCredit.getPoundageCompanyRate(),(short)4);
                    if (pRateNum == 0){
                        return pRateNum;
                    }
                }
            }
        }
        return row;
    }

    @Transactional
    @Override
    public int updateProductCredit(EntProductCredit productCredit) throws Exception{
        UserInfo userInfo = UserContextUtil.getUserInfo();
        //先把这条数据逻辑删除 在重新添加一条
        EntProductCredit oldProductCredit = new EntProductCredit();
        oldProductCredit.setId(productCredit.getId());
        oldProductCredit.setUpdateUser(userInfo.getAccount());
        oldProductCredit.setUpdateTime(new Date());
        oldProductCredit.setDeleteFlag((short)1);
        entProductCreditMapper.updateByPrimaryKeySelective(oldProductCredit);
        //再逻辑删除关联的 阶梯费率
        Map map = new HashMap();
        map.put("productCreditId",productCredit.getId());
        map.put("batch",productCredit.getBatch());
        map.put("deletedFlag",(short)1);
        entProductLadderMapper.updateProductLadder(map);
        //公司占比
        entProductCompanyRateMapper.updateProductCompanyRate(map);
        //重新添加一条
        productCredit.setId(CreateIDUtil.getId());
        productCredit.setCompanyCode(userInfo.getCompanyCode());
        productCredit.setCreateUser(userInfo.getAccount());
        productCredit.setCreateTime(new Date());
        productCredit.setUpdateUser(userInfo.getAccount());
        productCredit.setUpdateTime(new Date());
        productCredit.setDeleteFlag((short)0);
        productCredit.setBatch(CreateIDUtil.getId()+"-"+ DateConversionUtil.getCurrentTime());
        int updateNum = addProductCredit(productCredit);
        return updateNum;
    }

    @Override
    public int updatePriductCreditStatus(Map map) throws Exception{
        return entProductCreditMapper.updatePriductCreditStatus(map);
    }

    @Override
    public EntProductCredit getDetailCreditById(String id) throws Exception{
        EntProductCredit productCredit = entProductCreditMapper.selectByPrimaryKey(id);
        Map map = new HashMap();
        List<Map> overdue = new ArrayList<>();
        List<Map> prement = new ArrayList<>();
        map.put("productCreditId",id);
        map.put("batch",productCredit.getBatch());
        List<EntProductLadder> ladderList = entProductLadderMapper.selectLadderByMap(map);
        // productCredit.setLadderList(ladderList);
        for (int i=0;i<ladderList.size();i++){
            if (ladderList.get(i).getType() == 0) {
                Map overdueMap = ladderOfType(ladderList.get(i));
                overdue.add(overdueMap);
            }
            if (ladderList.get(i).getType() == 1) {
                Map paymentMap = ladderOfType(ladderList.get(i));
                prement.add(paymentMap);
            }
        }
        productCredit.setOverdueLadder(overdue);
        productCredit.setPrepaymentLadder(prement);
        List<EntProductCompanyRate> companyRateList = entProductCompanyRateMapper.selectCompanyRateByMap(map);
        //productCredit.setCompanyRateList(companyRateList);
        List<Map> overdueRateList = new ArrayList<>();
        List<Map> prementRateList = new ArrayList<>();
        List<Map> cashRateList = new ArrayList<>();
        List<Map> serviceRateList = new ArrayList<>();
        List<Map> pondageRateList = new ArrayList<>();
        //类型: 0逾期违约公司占比 1提前还款公司占比 2保证金 3服务费 4手续费'
        for (int i=0;i<companyRateList.size();i++){
            if (companyRateList.get(i).getType() == 0) {
                Map overdueRate = companyRateOfType(companyRateList.get(i));
                overdueRateList.add(overdueRate);
            }
            if (companyRateList.get(i).getType() == 1) {
                Map paymentRate = companyRateOfType(companyRateList.get(i));
                prementRateList.add(paymentRate);
            }
            if (companyRateList.get(i).getType() == 2) {
                Map cashRate = companyRateOfType(companyRateList.get(i));
                cashRateList.add(cashRate);
            }
            if (companyRateList.get(i).getType() == 3) {
                Map serviceRate = companyRateOfType(companyRateList.get(i));
                serviceRateList.add(serviceRate);
            }
            if (companyRateList.get(i).getType() == 4) {
                Map pondageRate = companyRateOfType(companyRateList.get(i));
                pondageRateList.add(pondageRate);
            }
        }
        productCredit.setOverdueCompanyRate(overdueRateList);
        productCredit.setPrepaymentCompanyRate(prementRateList);
        productCredit.setCashCompanyRate(cashRateList);
        productCredit.setServiceCompanyRate(serviceRateList);
        productCredit.setPoundageCompanyRate(pondageRateList);
        return productCredit;
    }

    public Map ladderOfType(EntProductLadder productLadder) throws Exception{
        Map map = new HashMap();
        map.put("minDay",productLadder.getMinDays());
        map.put("maxDay",productLadder.getMaxDays()==null?0:productLadder.getMaxDays());
        map.put("amountRate",productLadder.getAmountRate());
        return map;
    }

    public Map companyRateOfType(EntProductCompanyRate productCompanyRate) throws Exception{
        Map map = new HashMap();
        map.put("companyId",productCompanyRate.getCompanyId());
        map.put("companyCode",productCompanyRate.getCompanyCode());
        map.put("companyName",productCompanyRate.getCompanyName());
        map.put("companyRate",productCompanyRate.getRate());
        return map;
    }

    @Override
    public List<Map> getPeriodsList(Map map) throws Exception{
        return entProductCreditMapper.getPeriodsList(map);
    }

    @Override
    public EntProductCredit getProductCreditByMap(Map map) throws Exception {
        return entProductCreditMapper.getProductCreditByMap(map);
    }

    @Override
    public List<Map> getProductNameList(Map map) {
        return entProductCreditMapper.getProductNameList(map);
    }

    //添加阶梯费率 type 0 逾期 1 提前还款
    public int addProductLadder(EntProductCredit productCredit,List<Map> ladderList,short type){
        String ladder = JSON.toJSONString(ladderList);
        JSONArray array = JSONArray.parseArray(ladder);
        EntProductLadder productLadder = new EntProductLadder();
        int ladderNum = 0;
        for(int i=0;i<array.size();i++) {
            JSONObject jsonResult = (JSONObject) array.get(i);
            int minDay = Integer.parseInt(jsonResult.get("minDay").toString());
            if (i != array.size()-1) {
                int maxDay = Integer.parseInt(jsonResult.get("maxDay").toString());
                productLadder.setMaxDays(maxDay);
                //校验阶梯费率 后一条最小值与前一条最大值相差是否为1
                int j=i+1;
                JSONObject chenckResult = (JSONObject) array.get(j);
                int checkMinDay = Integer.parseInt(chenckResult.get("minDay").toString());
                if (checkMinDay - maxDay != 1) {
                    return 9;
                }
            }else {
                //阶梯最后一栏 max值置null
                productLadder.setMaxDays(null);
            }
            BigDecimal amountRate = new BigDecimal(jsonResult.get("amountRate").toString());
            productLadder.setId(CreateIDUtil.getId());
            productLadder.setProductCreditId(productCredit.getId());
            productLadder.setCompanyCode(productCredit.getCompanyCode());
            productLadder.setMinDays(minDay);
            productLadder.setAmountRate(amountRate);
            productLadder.setDeleteFlag((short)0);
            productLadder.setType(type);
            productLadder.setBatch(productCredit.getBatch());
            ladderNum = ladderNum + entProductLadderMapper.insertSelective(productLadder);
        }
        if(ladderNum != array.size()){
            return 0;
        }
        return 1;
    }

    //添加公司占比 type 0逾期违约公司占比 1提前还款公司占比 2保证金 3服务费 4手续费
    public int addCompanyRate(EntProductCredit productCredit,List<Map> companyRateList,short type){
        String companyRate = JSON.toJSONString(companyRateList);
        JSONArray array = JSONArray.parseArray(companyRate);
        int rateNum = 0;
        for(int i=0;i<array.size();i++) {
            JSONObject jsonResult = (JSONObject) array.get(i);
            String compaynId = jsonResult.get("companyId").toString();
            String companyCode = jsonResult.get("companyCode").toString();
            String companyName = jsonResult.get("companyName").toString();
            BigDecimal rate = new BigDecimal(jsonResult.get("companyRate").toString());
            EntProductCompanyRate productCompanyRate = new EntProductCompanyRate();
            productCompanyRate.setId(CreateIDUtil.getId());
            productCompanyRate.setProductCreditId(productCredit.getId());
            productCompanyRate.setCompanyId(compaynId);
            productCompanyRate.setCompanyCode(companyCode);
            productCompanyRate.setCompanyName(companyName);
            productCompanyRate.setRate(rate);
            productCompanyRate.setDeleteFlag((short)0);
            productCompanyRate.setType(type);
            productCompanyRate.setKind((short)0);
            productCompanyRate.setBatch(productCredit.getBatch());
            rateNum = rateNum + entProductCompanyRateMapper.insertSelective(productCompanyRate);
        }
        if(rateNum != array.size()){
            return 0;
        }
        return 1;
    }

}
