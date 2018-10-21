package cn.fintecher.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangtao
 * @Date: 2018/05/15 09:15
 * @Description: 将xml字符串转换成map
 */
public class XmlToMapUtil {

    public static Map<String,Object> xmlFile2Map(File xmlFile) {
        Map<String, Object> map=new HashMap<String,Object>();
        try {
            InputStream is = new FileInputStream(xmlFile);
            //创建解析器
            SAXReader sax=new SAXReader();
            //创建对应的Document对象
            Document doc=sax.read(is);
            //获取XML文档的根节点对象
            Element root=doc.getRootElement();
            //获取根节点下的所有的子节点
            List<Element> list = root.elements();
            //遍历根节点下的所有子节点并将其放入MAP对象中
            for (Element ele : list) {
                map.put(ele.getName(), ele.getText());
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/15 09:19
     * @Params: xmlStr（需要转换的xml字符串）
     * @Description: 将xml转换成map
     * @return: Map
     */
    public static Map<String,Object> xmlStr2Map(String xmlStr) {
        Document doc = null;
        Map map = new HashMap();
        try {
            // 将字符串转为XML
            doc = DocumentHelper.parseText(xmlStr);
            // 获取根节点
            Element rootElt = doc.getRootElement();
            if (rootElt.nodeCount()>0) {
                map = XmlToMapUtil.getNnode(rootElt);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }

    static Map  getNnode(Element element) {
        Map map = new HashMap();
        for (int i=0;i<element.nodeCount(); i++){
            try {
                Element t = (Element) element.node(i);
                // 获取t节点下的子节点
                Iterator iter = t.elementIterator();
                // 拿到t节点的key值
                String key = t.getName();
                // 拿到t节点的value值
                String value = t.getText();
                System.out.println("key:" + key + "value:" + value);
                map.put(key, value);
                while (iter.hasNext()) {
                    key="";
                    value="";
                    Element recordEle = (Element) iter.next();
                    // 拿到t节点下的子节点entry的key值
                    key = recordEle.getName();
                    // 拿到t节点下的子节点entry的value值
                    value = recordEle.getText();
                    map.put(key, value);
                    if(recordEle.nodeCount()>0){
                        int ss=recordEle.attributeCount();
                        XmlToMapUtil.getNnode(recordEle);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
