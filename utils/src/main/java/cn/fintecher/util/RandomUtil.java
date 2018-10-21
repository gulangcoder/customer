package cn.fintecher.util;

import java.util.Random;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : 生成随机数</strong><br>
 * <strong>Create on : 2018年05月14日 17:07<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:程锐 <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */

public class RandomUtil {

    private static Random randGen = null;
    private static Random randGen1 = null;
    private static char[] numbersAndLetters = null;
    private static char[] numbersAndLetters1 = null;

    /**
     * @Description: 产生指定长度的数字随机数
     * @Param: [指定长度: length]
     * @return: java.lang.String
     * @Date: 2018/5/14
     */
    public static final String randomForNum(int length) {
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            randGen = new Random();
            numbersAndLetters = ("0123456789").toCharArray();
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
        }
        return new String(randBuffer);
    }

    /**
     * @Description: 产生指定长度的数字和英文字母组合随机数
     * @Param: [指定长度: length]
     * @return: java.lang.String
     * @Date: 2018/5/14
     */
    public static final String randomForNumAndChar(int length) {
        if (length < 1) {
            return null;
        }
        if (randGen1 == null) {
            randGen1 = new Random();
            numbersAndLetters1 = ("0123456789abcdefghijklmnopqrstuvwxyz"
                    + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        }
        char[] randBuffer1 = new char[length];
        for (int i = 0; i < randBuffer1.length; i++) {
            randBuffer1[i] = numbersAndLetters1[randGen1.nextInt(71)];
        }
        return new String(randBuffer1);
    }

    /**
     * @Description: 返回长度为【strLength】的随机数,即获取小数点后strLength长度的随机数，包含`.`
     * @Param: [指定长度: strLength]
     * @return: java.lang.String
     * @Date: 2018/5/14
     */
    public static String getFixLenthString(int strLength) {
        Random rm = new Random();
        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
    }

    /**
    * @Description: 获取指定长度数字加字母组合, 经测试只会产生数字和大写字母的组合
    * @Param: [指定长度: length]
    * @return: java.lang.String
    * @Date: 2018/5/14
    */
    public static String createRandomCharData(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();//随机用以下三个随机生成器
        Random randdata = new Random();
        int data = 0;
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(2);
            //目的是随机选择生成数字，大小写字母
            switch (index) {
                case 0:
                    data = randdata.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data = randdata.nextInt(26) + 65;//保证只会产生大写字母
                    sb.append((char) data);
                    break;
                case 2:
                    data = randdata.nextInt(26) + 97;//保证只会产生小写字母
                    sb.append((char) data);
                    break;
            }
        }
        return sb.toString();
    }

    public synchronized static String createToke() {
        return new java.math.BigInteger(165, new Random()).toString(36).toUpperCase();
    }
}
