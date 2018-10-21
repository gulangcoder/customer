package cn.fintecher.app.service.sys;

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
public interface DictService {

    //查询数据字典
    List<Map> getDictByItemCode(String itemCode, String companyCode)throws Exception;

    String getDictNameByDictItemAndDetailCode(String itemCode,String detailCode)throws Exception;
}
