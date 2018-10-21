package cn.fintecher.system.service.impl;

import cn.fintecher.system.mapper.EntPromotionMapper;
import cn.fintecher.system.mapper.EntPromotionRuleMapper;
import cn.fintecher.system.model.EntPromotion;
import cn.fintecher.system.model.EntPromotionRule;
import cn.fintecher.system.service.ActivityService;
import cn.fintecher.util.CreateIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description :
 * Create on : 2018年05月24日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username: gq
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private EntPromotionMapper entPromotionMapper;

    @Autowired
    private EntPromotionRuleMapper entPromotionRuleMapper;

    /**
     * @Author: gq
     * @Date: 2018/06/12
     * @Description: 获取活动列表
     * @return:
     */
    @Override
    public List<EntPromotion> getEntPromotionList(Map map) throws Exception{
        List<EntPromotion> list = entPromotionMapper.getEntPromotionList(map);
        return list;
    }


    /**
     * @Author: gq
     * @Date: 2018/06/12
     * @Description: 新增活动
     * @return:
     */
    @Transactional
    @Override
    public Map addEntPromotion(EntPromotion entPromotion, List<EntPromotionRule> list) throws Exception{
        //生成主键
        String entPromotionId = CreateIDUtil.getId();
        entPromotion.setId(entPromotionId);
        entPromotionMapper.insertSelective(entPromotion);

        for (EntPromotionRule entPromotionRule:list) {
            //生成主键
            String entPromotionRuleId = CreateIDUtil.getId();
            entPromotionRule.setId(entPromotionRuleId);
            entPromotionRule.setPromotionCode(entPromotionId);
        }
        entPromotionRuleMapper.insertList(list);

        Map map = new HashMap();
        map.put("flag", true);
        map.put("msg", "message.system.save.success");
        return map;
    }

    /**
     * @Author: gq
     * @Date: 2018/06/12
     * @Description: 修改活动
     * @return:
     */
    @Transactional
    @Override
    public Map updateEntPromotion(EntPromotion entPromotion, List<EntPromotionRule> list) throws Exception{
        String entPromotionId = entPromotion.getId();
        //修改活动
        entPromotionMapper.updateByPrimaryKeySelective(entPromotion);
        //删除活动规则
        entPromotionRuleMapper.deleteByPromotionCode(entPromotionId);
        //插入新的活动规则
        for (EntPromotionRule entPromotionRule:list) {
            //生成主键
            String entPromotionRuleId = CreateIDUtil.getId();
            entPromotionRule.setId(entPromotionRuleId);
            entPromotionRule.setPromotionCode(entPromotionId);
        }
        entPromotionRuleMapper.insertList(list);

        Map map = new HashMap();
        map.put("flag",true);
        map.put("msg","message.system.update.success");
        return map;
    }

    /**
     * @Author: gq
     * @Date: 2018/06/12
     * @Description: 删除活动
     * @return:
     */
    @Transactional
    @Override
    public Map delEntPromotion(String id) throws Exception{
        //删除活动
        entPromotionMapper.deleteById(id);
        //删除活动规则
        entPromotionRuleMapper.deleteByPromotionCode(id);
        Map map = new HashMap();
        map.put("flag",true);
        map.put("msg","message.system.delete.success");
        return map;
    }

    /**
     * @Author: gq
     * @Date: 2018/06/12
     * @Description: 查询活动
     * @return:
     */
    @Override
    public Map selEntPromotion(String id) throws Exception{
        EntPromotion entPromotion = entPromotionMapper.selectById(id);
        List<EntPromotionRule> list = entPromotionRuleMapper.selectByPromotionCode(id);
        Map map = new HashMap();
        map.put("entPromotion",entPromotion);
        map.put("list",list);
        return map;
    }

    /**
     * @Author: wangtao
     * @Date: 2018/06/14 11:03
     * @Params: companyCode:企业code
     * @Description: 获取有效的活动
     * @return: List
     */
    @Override
    public List<EntPromotion> getEffectiveActivity(String companyCode) throws Exception{
        return entPromotionMapper.getEffectiveActivity(companyCode);
    }

    /**
     * @Author: gq
     * @Date: 2018/06/12
     * @Description: 更新活动状态
     * @return:
     */
    @Override
    public Map updateStatus(EntPromotion entPromotion) throws Exception{
        int n = entPromotionMapper.updateByPrimaryKeySelective(entPromotion);
        Map map = new HashMap();
        if(n>0){
            map.put("flag",true);
            map.put("msg","message.system.operation.success");
        }else{
            map.put("flag",false);
            map.put("msg","message.system.operation.fail");
        }
        return map;
    }
}
