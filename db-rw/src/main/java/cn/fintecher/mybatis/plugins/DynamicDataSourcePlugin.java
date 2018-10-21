package cn.fintecher.mybatis.plugins;
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


import cn.fintecher.database.DynamicDataSourceGlobal;
import cn.fintecher.database.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.Properties;


@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {
                MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {
                MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class})})
@Component
@Slf4j
public class DynamicDataSourcePlugin implements Interceptor {

    /**
     * 插件方法查询
     */
    private static final String QUERY = "query";


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();
        if (QUERY.equals(methodName)) {
            System.out.println("切换read数据源");
            log.trace("切换read数据源");
            DynamicDataSourceHolder.setDataSourceType(DynamicDataSourceGlobal.DATA_READ_TYPE);
        }
        //else 使用默认主数据源
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        //
    }
}
