package cn.fintecher.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : 图片压缩工具类</strong><br>
 * <strong>Create on : 2018年05月14日 16:22<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 * username:程锐 <br>
 * email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 * <br>
 * <strong>修改历史:</strong><br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 * <br>
 * <br>
 */
public class ImageZipUtil {

    /**
    * @Description: 根据设置的宽高等比例压缩图片文件<br> 先保存原文件，再压缩、上传
    * @Param: [要进行压缩的文件: oldFile, 新文件: newFile, 宽度 //设置宽度时（高度传入0，等比例缩放: width, 高度 //设置高度时（宽度传入0，等比例缩放）: height]
    * @return: java.lang.String 返回压缩后的文件的全路径
    * @Date: 2018/5/14
    */
    public static String zipImageFile(File oldFile, File newFile, int width, int height) {
        if (oldFile == null) {
            return null;
        }
        try {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(oldFile);
            int w = srcFile.getWidth(null);
            int h = srcFile.getHeight(null);
            double bili;
            if(width>0){
                bili=width/(double)w;
                height = (int) (h*bili);
            }else{
                if(height>0){
                    bili=height/(double)h;
                    width = (int) (w*bili);
                }
            }
            String srcImgPath = newFile.getAbsoluteFile().toString();
            String subfix = "jpg";
            subfix = srcImgPath.substring(srcImgPath.lastIndexOf(".")+1,srcImgPath.length());
            String prefix = srcImgPath.substring(0, srcImgPath.lastIndexOf("."));
            BufferedImage buffImg = null;
            if(subfix.equals("png")){
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            }else{
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            }
            // 压缩以后新图片路径
            String newImagePaeh = prefix + "_w" + width + "_h" + height + "." + subfix;
            Graphics2D graphics = buffImg.createGraphics();
            graphics.setBackground(new Color(255,255,255));
            graphics.setColor(new Color(255,255,255));
            graphics.fillRect(0, 0, width, height);
            graphics.drawImage(srcFile.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            newFile = new File(newImagePaeh);
            ImageIO.write(buffImg, subfix, newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile.getAbsolutePath();
    }

    /**
    * @Description: 按自定义设置的宽度高度压缩图片文件<br> 先保存原文件，再压缩、上传
    * @Param: [要进行压缩的文件全路径: oldFile, 新文件: newFile, 宽度: width, 高度: height]
    * @return: java.lang.String 返回压缩后的文件的全路径
    * @Date: 2018/5/14
    */
    public static String zipWidthHeightImageFile(File oldFile,File newFile, int width, int height) {
        if (oldFile == null) {
            return null;
        }
        String srcImgPath = null;
        try {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(oldFile);
            srcImgPath = newFile.getAbsoluteFile().toString();
            String subfix = "jpg";
            subfix = srcImgPath.substring(srcImgPath.lastIndexOf(".")+1,srcImgPath.length());
            BufferedImage buffImg = null;
            if(subfix.equals("png")){
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            }else{
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            }
            String prefix = srcImgPath.substring(0, srcImgPath.lastIndexOf("."));
            // 压缩以后新图片路径
            String newImagePaeh = prefix + "_w" + width + "_h" + height + "." + subfix;
            Graphics2D graphics = buffImg.createGraphics();
            graphics.setBackground(new Color(255,255,255));
            graphics.setColor(new Color(255,255,255));
            graphics.fillRect(0, 0, width, height);
            graphics.drawImage(srcFile.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            newFile = new File(newImagePaeh);
            ImageIO.write(buffImg, subfix, newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile.getAbsolutePath();
    }

    /**
    * @Description:  获取图片名称
    * @Param: [图片路径: src]
    * @return: java.lang.String
    * @Date: 2018/5/14
    */
    public static String getName(String src) {
        String[] arr = null;
        if (src.indexOf("\\") != -1) { //windows路径方式
            arr = src.split("\\\\");
        } else {
            arr = src.split("/"); //Linux路径方式
        }
        return Array.get(arr, arr.length - 1).toString();
    }

    /**
    * @Description:  获取url
    * @Param: [以指定分隔符拼接的路径: urls]
    * @return: java.lang.String[]
    * @Date: 2018/5/14
    */
    public static String[] getUrl(String urls) {
        return urls.split(";");
    }


    /**
    * @Description:  判断一个文件是否是图片
    * @Param: [需要判断的文件: file]
    * @return: boolean
    * @Date: 2018/5/15
    */
    public static boolean isImage(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            if (image == null) {
                return false;
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

}
