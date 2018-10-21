package cn.fintecher.system.service;

import cn.fintecher.system.model.EntPromotion;
import cn.fintecher.system.model.EntPromotionRule;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description :
 * Create on : 2018年06月12日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username: gq
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
public interface ActivityService {
    /**
     * @Author: gq
     * @Date: 2018/06/12
     * @Description: 获取活动列表
     * @return:
     */
    List<EntPromotion> getEntPromotionList(Map map) throws Exception;

    /**
     * @Author: gq
     * @Date: 2018/06/12
     * @Description: 新增活动
     * @return:
     */
    Map addEntPromotion(EntPromotion entPromotion, List<EntPromotionRule> list) throws Exception;

    /**
     * @Author: gq
     * @Date: 2018/06/12
     * @Description: 修改活动
     * @return:
     */
    Map updateEntPromotion(EntPromotion entPromotion, List<EntPromotionRule> list) throws Exception;

    /**
     * @Author: gq
     * @Date: 2018/06/12
     * @Description: 删除活动
     * @return:
     */
    Map delEntPromotion(String id) throws Exception;

    /**
     * @Author: gq
     * @Date: 2018/06/12
     * @Description: 查询活动
     * @return:
     */
    Map selEntPromotion(String id) throws Exception;

    List<EntPromotion> getEffectiveActivity(String companyCode) throws Exception;


    /**
     * @Author: gq
     * @Date: 2018/06/12
     * @Description: 更新活动状态
     * @return:
     */
    Map updateStatus(EntPromotion entPromotion) throws Exception;
}
