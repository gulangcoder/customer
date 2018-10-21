package cn.fintecher.common.mybatis.converter;

import java.io.Serializable;

/**
 * Title :
 * Description : @自定义类型, 只要是定义了该类型的属性会被 AESEncryptHandler拦截@
 * Create on : 2018年05月29日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:chengrui
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
public class MyString implements Serializable, CharSequence, Comparable<String> {

    private static final long serialVersionUID = 1L;

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MyString() {
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public char charAt(int index) {
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }

    @Override
    public int compareTo(String o) {
        return 0;
    }
}
