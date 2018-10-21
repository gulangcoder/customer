package cn.fintecher.system.service;

import cn.fintecher.system.model.EntProductCredit;
import cn.fintecher.system.model.EntProductDetail;

import java.util.List;
import java.util.Map; /**
 * @Auther: liangdeng
 * @Date: 2018/9/5
 * @Description:
 */
public interface ProductDetailService {
    /**
     * @description: 根据产品id查询产品详情列表
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    List<EntProductDetail> getProductDeatilList(Map map) throws Exception;

    /**
     * @description: 校验产品序列是否重复
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    EntProductDetail checkProductSequence(Map map) throws Exception;

    /**
     * @description: 校验产品名称是否重复
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    EntProductDetail checkProductName(Map map) throws Exception;

    /**
     * @description: 添加产品详情
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    int addProductDetail(EntProductDetail productDetail)  throws Exception;

    /**
     * @description: 修改产品详情
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    int updatePriductDteail(EntProductDetail productDetail)  throws Exception;

    /**
     * @description: 获取产品详情
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/6
     */
    EntProductDetail getProductDeatilById(String productDetailId) throws Exception;

    /**
     * @description: 查询某产品详情下信率是否都是停用状态
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/6
     */
    List<EntProductCredit> selectCreditListByMap(Map statusMap) throws Exception;

    /**
     * @description: 修改产品详情状态
     * @param:
     * @return: 
     * @auther: liangdeng
     * @date: 2018/9/6 
     */
    int updatePriductDteailStatus(Map map) throws Exception;

    /**
     * @description:产品名称列表
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/6
     */
    List<EntProductDetail> getProductNameList(Map map) throws Exception;
}
