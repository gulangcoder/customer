package cn.fintecher.system.service;

import cn.fintecher.system.model.EntProductCredit;

import java.util.List;
import java.util.Map; /**
 * @Auther: liangdeng
 * @Date: 2018/9/6
 * @Description:
 */
public interface ProductCreditService {

    /**
     * @description: 产品信贷费率列表
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/7
     */
    List<EntProductCredit> getProductCreditList(Map map) throws Exception;

    /**
     * @description: 添加产品信率
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/7
     */
    int addProductCredit(EntProductCredit productCredit) throws Exception;

    /**
     * @description: 修改产品信率
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/7
     */
    int updateProductCredit(EntProductCredit productCredit) throws Exception;

    /**
     * @description: 修改产品信率状态
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/7
     */
    int updatePriductCreditStatus(Map map) throws Exception;

    /**
     * @description: 获取产品信率详情
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/7
     */
    EntProductCredit getDetailCreditById(String id) throws Exception;

    /**
     * @description: 获取期数
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/7
     */
    List<Map> getPeriodsList(Map map) throws Exception;

    /**
     * @description: 校验期数、期限不能重复
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/7
     */
    EntProductCredit getProductCreditByMap(Map map) throws Exception;
    /**
     * @description: 获取产品名称
     * @param:
     * @return:
     */
    List<Map> getProductNameList(Map map);
}
