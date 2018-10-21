package cn.fintecher.util;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.io.*;

/**
 * <strong>Title :<br>
 * </strong> <strong>Description :Base64位图片的加密和解密工具类</strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2018年05月15日<br>
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
public class Base64Util {

    /**
     * @Description: Base64图片加密
     * @Author:
     * @CreateTime:
     * @param imgFile 需要加密的图片文件路径
     * @return
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Base64图片加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * @Description: 将base64编码字符串转换为图片
     * @Author: zhangtao
     * @CreateTime: 2018年05月15日
     * @param imgStr base64编码字符串
     * @param path  图片路径-具体到文件
     * @return
     */
    public static boolean generateImage(String imgStr, String path) {
        if (imgStr == null || imgStr.length() <= 0) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * @Description: 将图片转换成byte数组
     * @Author: zhangtao
     * @CreateTime: 2018年05月15日
     * @param path  图片路径-具体到文件
     * @return
     */
    public static byte[] imageToByte(String path) {
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

    /**
     * @Description: 将byte数组转化成图片
     * @Author: zhangtao
     * @CreateTime: 2018年05月15日
     * @param path  转换后的图片路径-具体到文件
     * @return
     */
    public static void byteToImage(byte[] data, String path) {
        if (data.length < 3 || path.equals("")) {
            return;
        }
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.flush();
            imageOutput.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
