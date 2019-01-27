package com.dawnyu.maple.utils;

import com.dawnyu.maple.wechat.entity.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xml处理工具类
 * @author dawn
 */
public class XmlUtil {
    /**
     * xml转map
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        HashMap<String, String> map = new HashMap<>(16);
        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);

        Element root = doc.getRootElement();
        @SuppressWarnings("unchecked")
        List<Element> list = (List<Element>)root.elements();

        for(Element e:list){
            map.put(e.getName(), e.getText());
        }
        ins.close();
        return map;
    }
    /**
     * 文本消息对象转xml
     */
    public static String textMsgToxml(TextMessage textMessage){
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }
}
