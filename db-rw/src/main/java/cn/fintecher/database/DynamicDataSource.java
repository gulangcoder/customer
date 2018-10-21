package cn.fintecher.database;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年07月05日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:zh-pc
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSourceType();
    }

}
