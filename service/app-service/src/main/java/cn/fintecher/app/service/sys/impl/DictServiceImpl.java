package cn.fintecher.app.service.sys.impl;

import cn.fintecher.app.mapper.sys.DictDetailMapper;
import cn.fintecher.app.model.sys.DictDetail;
import cn.fintecher.app.service.sys.DictService;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.util.redis.RedisKeyConstants;
import cn.fintecher.util.redis.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月19日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDetailMapper dictDetailMapper;

    @Override
    public List<Map> getDictByItemCode(String itemCode, String companyCode)throws Exception {
        List<Map> dictDetailList =RedisUtil.get(RedisKeyConstants.DICT_ITEM_CODE+companyCode+"_"+itemCode);
        if(dictDetailList==null||dictDetailList.size()<1){
            dictDetailList = new ArrayList<>();
            Map param = new HashMap();
            param.put("itemCode",itemCode);
            param.put("companyCode",companyCode);
            List<DictDetail>dictDetails = dictDetailMapper.findList(param);
            for (DictDetail detail:dictDetails) {
                if(detail.getStatus().shortValue()!=1){
                    continue;
                }
                Map dMap = new HashMap();
                dMap.put("value",detail.getDetailCode());
                dMap.put("text",detail.getDetailName());
                dMap.put("itemCode",detail.getItemCode());
                dictDetailList.add(dMap);
            }
        }
        return dictDetailList;
    }

    /**
     * 根据字典项还有字典的code 获取字典明细名称
     * @param itemCode 字典明细字典项
     * @param detailCode 字典明细code
     * @return 字典明细名称
     */
    public String getDictNameByDictItemAndDetailCode(String itemCode,String detailCode)throws Exception{
        String companyCode = UserContextUtil.getUserInfo().getCompanyCode();
        String detailName = "";
        if(StringUtils.isEmpty(detailCode)){
            return "";
        }
        List<Map> dictDetails =   RedisUtil.get(RedisKeyConstants.DICT_ITEM_CODE+companyCode+"_"+itemCode);
        if(dictDetails.size()>0){
            for(Map map :dictDetails){
                String dictCode = map.get("dictCode").toString();
                if(dictCode.equals(detailCode)){
                    return  map.get("dictName").toString();
                }
            }
        }
        //redis没有信息
        Map param = new HashMap();
        param.put("itemCode",itemCode);
        param.put("detailCode",detailCode);
        param.put("companyCode",companyCode);
        DictDetail  dictDetail = dictDetailMapper.getDictDetailByItemCodeAndCode(param);
        detailName = (dictDetail==null?"":dictDetail.getDetailName());
        return detailName;
    }
}