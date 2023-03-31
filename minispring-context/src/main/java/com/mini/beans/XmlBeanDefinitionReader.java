package com.mini.beans;

import com.mini.context.BeanDefinition;
import com.mini.core.Resource;
import org.dom4j.Element;

/**
 * @author fanxiao 2023/3/31
 * @since 1.0.0
 */
public class XmlBeanDefinitionReader {

  private BeanFactory beanFactory;

  public XmlBeanDefinitionReader(BeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  public void loadBeanDefinitions(Resource resource) {
    while (resource.hasNext()) {
      Element element = (Element) resource.next();
      String beanId = element.attributeValue("id");
      String beanClass = element.attributeValue("class");
      BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClass);
      beanFactory.registerBeanDefinition(beanDefinition);
    }
  }
}
