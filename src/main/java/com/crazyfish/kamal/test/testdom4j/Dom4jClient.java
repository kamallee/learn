package com.crazyfish.kamal.test.testdom4j;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

import static java.lang.System.*;

/**
 * Created by kamal on 15/8/18.
 */
public class Dom4jClient {
    public static void main(String args[]) {
        try {
            SAXReader reader = new SAXReader();
            File file = new File("/Users/kamal/lipengpeng/kamalleeproject/src/main/resources/conf/xmltest.xml");
            Document d = reader.read(file);
            Element el = d.getRootElement();
            Iterator it = el.elementIterator();
            while (it.hasNext()) {
                Element element = (Element) it.next();

                //未知属性名称情况下
                /*Iterator attrIt = element.attributeIterator();
                while (attrIt.hasNext()) {
                    Attribute a  = (Attribute) attrIt.next();
                    System.out.println(a.getValue());
                }*/

                //已知属性名称情况下
                out.println("id: " + element.attributeValue("id"));

                    //未知元素名情况下
                /*Iterator eleIt = element.elementIterator();
                while (eleIt.hasNext()) {
                    Element e = (Element) eleIt.next();
                    System.out.println(e.getName() + ": " + e.getText());
                }
                System.out.println();*/

                //已知元素名情况下
                out.println("title: " + element.elementText("name"));
                out.println("author: " + element.elementText("id"));
                Iterator it1 = element.elementIterator();
                if( it1.hasNext() ){
                    Element elemen = (Element)it1.next();
                    out.println("tx1: " + elemen.getTextTrim());
                    out.println("tx1: " + element.elementText("tx"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
