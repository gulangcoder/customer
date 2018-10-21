package cn.fintecher.common.mybatis.converter;

import cn.fintecher.util.AesUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Title :
 * Description : @mybatis自定义数据加密, 加密方式AES@
 * Create on : 2018年05月29日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:chengrui
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
public class AESEncryptHandler extends BaseTypeHandler<String> {

    /**
    * @Description: 解密
    * @Param:
     * * @param cs  CallableStatement对象
     * * @param columnIndex  查询列的下标
    * @return: java.lang.String
    * @Date: 2018/5/29
    */
    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        return AesUtil.decrypt(result);
    }


    /**
    * @Description: 通过lie的下标解密
    * @Param:
     * * @param rs  ResultSet对象
     * @param columnIndex  列的下标
    * @return: java.lang.String
    * @Date: 2018/5/29
    */
    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        return AesUtil.decrypt(result);
    }

    /**
     * @Description:  加密方法,在执行变更数据的时候使用
     * @Param:
     * * @param ps PreparedStatement对象
     * * @param i  位置
     * * @param parameter 需要加密的字符串
     * * @param jdbcType  类型
     * @return: void
     * @Date: 2018/5/29
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, AesUtil.encrypt(parameter));
    }

    /**
    * @Description: 通过字段名解密
    * @Param:
     * * @param rs  ResultSet对象
     * @param columnName   列名
    * @return: java.lang.String
    * @Date: 2018/5/29
    */
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        return AesUtil.decrypt(result);
    }
}
