package cn.fintecher.system.service;

import cn.fintecher.common.contract.EntContractBookDetail;
import cn.fintecher.system.model.EntContractTemplet;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月05日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
public interface EntContractBookDetailService {

    /**
     * 查询数据列表
     * @param param
     * @return
     */
    List<Map> getList(Map param)throws Exception;


    /**
     * 根据id查询
     * @param id
     * @return
     */
    EntContractBookDetail getById(String id)throws Exception;


    /**
     * 保存
     * @param companyCode
     * @param custId
     * @return
     */
    Map saveContractBookDetail(String companyCode,String custId,String custName,String custCardNo,String productId,String contractType);


    /**
     * 根据ids集合查询
     * @param ids
     * @return
     */
    List<String> getListByIds(List<String>ids)throws Exception;


    /**
     * 生成合同内容
     * @return
     */
    Map generateBookContract(Map data)throws Exception;


    Map generateBookContract(EntContractBookDetail entContractBookDetail)throws Exception;

    List<EntContractTemplet> getProtocol(Map parm);
}
