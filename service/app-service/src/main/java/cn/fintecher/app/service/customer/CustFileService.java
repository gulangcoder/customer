package cn.fintecher.app.service.customer;

import cn.fintecher.app.model.customer.EntCustFile;
import cn.fintecher.app.model.customer.EntCustProductApply;
import cn.fintecher.app.model.customer.EntProduct;

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
public interface CustFileService {

    /**
     * 查询用户文件
     * @param custId
     * @param fileType
     * @return
     */
    List<EntCustFile> getCustFile(String custId, String fileType, String productId)throws Exception;

    /**
     * 更新用户文件
     * @param entCustFile
     * @return
     */
    Map updateCustFile(EntCustFile entCustFile)throws Exception;

    Map updateCustApplyStatus(Map map) throws Exception;

    EntProduct getProductVideo(String productId) throws Exception;

    EntCustProductApply getVideo() throws Exception;
}
