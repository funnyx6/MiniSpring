package com.mini.beans;

import com.mini.context.BeanDefinition;
import com.mini.core.Resource;
import org.dom4j.Element;

/**
 * @author fanxiao 2023/3/31
 * @since 1.0.0
 */
public class XmlBeanDefinitionReader {

  private SimpleBeanFactory simpleBeanFactory;

  public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
    this.simpleBeanFactory = simpleBeanFactory;
  }

  public void loadBeanDefinitions(Resource resource) {
    while (resource.hasNext()) {
      Element element = (Element) resource.next();
      String beanId = element.attributeValue("id");
      String beanClass = element.attributeValue("class");
      BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClass);
      simpleBeanFactory.registerBeanDefinition(beanDefinition);
    }
  }
}
