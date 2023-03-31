package com.mini.core;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * @author fanxiao 2023/3/31
 * @since 1.0.0
 */
public class ClassPathXmlResource implements Resource {
  Document document;
  Element rootElement;
  Iterator<Element> elementIterator;

  public ClassPathXmlResource(String fileName) {
    try {
      SAXReader saxReader = new SAXReader();
      URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
      document = saxReader.read(xmlPath);
      rootElement = document.getRootElement();
      elementIterator = rootElement.elementIterator();
    } catch (Exception e) {

    }
  }

  @Override
  public boolean hasNext() {
    return this.elementIterator.hasNext();
  }

  @Override
  public Object next() {
    return this.elementIterator.next();
  }
}
