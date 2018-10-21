package cn.fintecher.util;

import org.apache.commons.lang.StringUtils;

/**
 * <strong>Title :<br>
 * </strong> <strong>Description : 脱敏工具类</strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2018年05月14日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:zhangtao <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class DesensitizationUtil {
    /**
     * [银行卡号] 其他用星号隐藏每位1个星号 <例子:6222600**********1234>
     *
     * @param bankNum     需要脱敏的数据
     * @param startLength 起始位置
     * @param endLength   结束位置
     * @return
     */
    public static String bankCard(String bankNum, Integer startLength, Integer endLength) {
        //如果为空直接返回空
        if (StringUtils.isBlank(bankNum)) {
            return "";
        }
        if (bankNum.length() <= startLength + endLength) {
            return bankNum;
        }
        String str = StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(bankNum, endLength), StringUtils.length(bankNum), "*"), "******");
        return StringUtils.left(bankNum, startLength).concat(str);
    }

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     *
     * @param fullName 需要脱敏的汉字
     * @param length   需要脱敏的位数
     * @return
     */
    public static String chineseName(String fullName, Integer length) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        if (fullName.length() <= length) {
            return fullName;
        }
        String name = StringUtils.left(fullName, length);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }

    /**
     * [身份证号] 显示最后几位，其他隐藏。共计18位或者15位。<例子：*************5762>
     *
     * @param cardNum 身份证号码
     * @param length  显示后几位
     * @return
     */
    public static String cardNumRight(String cardNum, Integer length) {
        if (StringUtils.isBlank(cardNum)) {
            return "";
        }
        if (cardNum.length() <= length) {
            return cardNum;
        }
        String num = StringUtils.right(cardNum, length);
        return StringUtils.leftPad(num, StringUtils.length(cardNum), "*");
    }

    /**
     * [身份证号] 显示前面四位，其他隐藏。共计18位或者15位。<例子：3424*************>
     *
     * @param cardNum 身份证号码
     * @param length  显示前几位
     * @return
     */
    public static String cardNumLeft(String cardNum, Integer length) {
        if (StringUtils.isBlank(cardNum)) {
            return "";
        }
        String num = StringUtils.left(cardNum, length);
        return StringUtils.leftPad(num, StringUtils.length(cardNum), "*");
    }

    /**
     * [身份证号] 显示前几位，后几位其他隐藏。共计18位或者15位。<例子：3424*********8077>
     *
     * @param cardNum     身份证号码
     * @param startLength 前几位
     * @param endLength   后几位
     * @return
     */
    public static String cardNum(String cardNum, Integer startLength, Integer endLength) {
        if (StringUtils.isBlank(cardNum)) {
            return "";
        }
        return StringUtils.left(cardNum, startLength).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(cardNum, endLength), StringUtils.length(cardNum), "*"), "******"));
    }

    /**
     * [手机号码] 前几位，后几位，其他隐藏<例子:138******1234>
     *
     * @param mobile 手机号码
     * @return
     */
    public static String mobile(String mobile, Integer startLength, Integer endLength) {
        if (StringUtils.isBlank(mobile)) {
            return "";
        }
        return StringUtils.left(mobile, startLength).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(mobile, endLength), StringUtils.length(mobile), "*"), "******"));
    }

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     *
     * @param email 邮箱
     * @return
     */
    public static String email(String email, Integer length) {
        if (StringUtils.isBlank(email)) {
            return "";
        }
        int index = StringUtils.indexOf(email, "@");
        if (index <= length) {
            return email;
        } else {
            return StringUtils.rightPad(StringUtils.left(email, length), index, "*").concat(StringUtils.mid(email, index, StringUtils.length(email)));
        }
    }

    /**
     * [地址] 只显示到地区，不显示详细地址<例子：北京市海淀区****>
     *
     * @param address 地址
     * @param length  敏感信息长度
     * @return
     */
    public static String address(String address, Integer length) {
        if (StringUtils.isBlank(address)) {
            return "";
        }
        return StringUtils.rightPad(StringUtils.left(address, StringUtils.length(address) - length), StringUtils.length(address), "*");
    }
}

