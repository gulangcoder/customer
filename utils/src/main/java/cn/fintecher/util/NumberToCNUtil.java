package cn.fintecher.util;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : 小写转大写工具类</strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2018年05月15日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
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
public class NumberToCNUtil {

    private static final String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";

    private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";

    private static final double MAX_VALUE = 9999999999999.99D;

    /**
     * 将小写金额转换成大写
     *
     * @param amount 需要转换的小写金额
     * @return
     */
    public static String changeAmountToBig(double amount) {
        if (amount < 0 || amount > MAX_VALUE) {
            return "参数非法!";
        }
        long l = Math.round(amount * 100);
        if (l == 0) {
            return "零元整";
        }
        String strValue = l + "";
        // i用来控制数
        int i = 0;
        // j用来控制单位
        int j = UNIT.length() - strValue.length();
        String rs = "";
        boolean isZero = false;
        for (; i < strValue.length(); i++, j++) {
            char ch = strValue.charAt(i);
            if (ch == '0') {
                isZero = true;
                if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元') {
                    rs = rs + UNIT.charAt(j);
                    isZero = false;
                }
            } else {
                if (isZero) {
                    rs = rs + "零";
                    isZero = false;
                }
                rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
            }
        }
        if (!rs.endsWith("分")) {
            rs = rs + "整";
        }
        rs = rs.replaceAll("亿万", "亿");
        return rs;
    }
}

