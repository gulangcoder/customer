package cn.fintecher.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * DOUBLE工具类
 *
 * @author zhangheng
 * @version 2018-04-10
 */
public class DoubleUtils {
    //默认保留位数
    private static final int DEF_DIV_SCALE = 2;

    /**
     * 两个Double数相加
     *
     * @param v1 数值1
     * @param v2 数值2
     * @return Double 相加结果
     */
    public static Double add(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).setScale(DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 两个Double数相减
     *
     * @param v1 数值1
     * @param v2 数值2
     * @return Double 相减结果
     */
    public static Double sub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).setScale(DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 两个Double数相乘
     *
     * @param v1 数值1
     * @param v2 数值2
     * @return Double 相乘结果
     */
    public static Double mul(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).setScale(DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 两个Double数相除
     *
     * @param v1 数值1
     * @param v2 数值2
     * @return Double 相除结果
     */
    public static Double div(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 两个Double数相除，并保留scale位小数
     *
     * @param v1    数值1
     * @param v2    数值2
     * @param scale 保留位数
     * @return Double 相除结果
     */
    public static Double div(Double v1, Double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 保留几位小数
     *
     * @param v     要操作的值
     * @param scale 保留位数
     * @return 结果
     */
    public static Double keepSpace(Double v, int scale) {
        if (null == v) {
            v = 0d;
        }
        return new BigDecimal(v.toString()).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 保留几位小数
     *
     * @param v 要操作的值
     * @return 结果
     */
    public static Double keepSpace(Double v) {
        if (null == v) {
            v = 0d;
        }
        return new BigDecimal(v.toString()).setScale(DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 保留几位小数
     *
     * @param v     要操作的值
     * @param scale 保留位数
     * @return 结果
     */
    public static Double keepSpace(String v, int scale) {
        if (null == v || "".equals(v.trim())) {
            v = "0.00";
        }
        return new BigDecimal(v).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 保留几位小数
     *
     * @param v 要操作的值
     * @return 结果
     */
    public static Double keepSpace(String v) {
        if (null == v || "".equals(v.trim())) {
            v = "0.00";
        }
        return new BigDecimal(v).setScale(DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 保留几位小数
     *
     * @param v     要操作的值
     * @param scale 保留位数
     * @return 结果
     */
    public static String keepSpaceStr(String v, int scale) {
        if (null == v || "".equals(v.trim())) {
            v = "0.00";
        }
        if (scale <= 0) {
            scale = 2;
        }
        String f = "0.";
        for (int i = 0; i < scale; i++) {
            f += "0";
        }
        DecimalFormat df = new DecimalFormat(f);
        return df.format(Double.valueOf(v));
    }

    /**
     * 保留几位小数
     *
     * @param v 要操作的值
     * @return 结果
     */
    public static String keepSpaceStr(String v) {
        if (null == v || "".equals(v.trim())) {
            v = "0.00";
        }
        String f = "0.";
        for (int i = 0; i < DEF_DIV_SCALE; i++) {
            f += "0";
        }
        DecimalFormat df = new DecimalFormat(f);
        return df.format(Double.valueOf(v));
    }

    /**
     * double转String
     *
     * @param v     要转的值
     * @param scale 保留位数
     * @return 结果
     */
    public static String keepSpaceStr(Double v, int scale) {
        if (null == v) {
            v = 0d;
        }
        if (scale <= 0) {
            scale = 2;
        }
        String f = "0.";
        for (int i = 0; i < scale; i++) {
            f += "0";
        }
        DecimalFormat df = new DecimalFormat(f);
        return df.format(v);
    }

    /**
     * double转String
     *
     * @param v 要转的值
     * @return 结果
     */
    public static String keepSpaceStr(Double v) {
        if (null == v) {
            v = 0d;
        }
        String f = "0.";
        for (int i = 0; i < DEF_DIV_SCALE; i++) {
            f += "0";
        }
        DecimalFormat df = new DecimalFormat(f);
        return df.format(v);
    }

    /**
     * 比较大小
     *
     * @param v1 数值1
     * @param v2 数值2
     * @return 结果（>0 v1>v2、=0 v1=v2、<0 v1<v2、null v1 or v2 is NUll）
     */
    public static Integer compare(Double v1, Double v2) {
        if (null == v1 || null == v2) {
            return null;
        }
        BigDecimal data1 = new BigDecimal(v1.toString());
        BigDecimal data2 = new BigDecimal(v2.toString());
        return data1.compareTo(data2);
    }

    /**
     * 判断是否相等
     *
     * @param v1 数值1
     * @param v2 数值1
     * @return 结果（true相等、false不相等）
     */
    public static boolean equals(Double v1, Double v2) {
        if (null == v1 && null == v2) {
            return true;
        } else if (null == v1 || null == v2) {
            return false;
        }
        int flag = compare(v1, v2);
        return 0 == flag ? true : false;
    }
}
