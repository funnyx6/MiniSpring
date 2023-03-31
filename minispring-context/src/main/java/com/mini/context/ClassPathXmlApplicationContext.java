package com.mini.context;

import com.mini.beans.BeanFactory;
import com.mini.beans.BeansException;
import com.mini.beans.SimpleBeanFactory;
import com.mini.beans.XmlBeanDefinitionReader;
import com.mini.core.ClassPathXmlResource;
import com.mini.core.Resource;

/**
 * @author fanxiao 2023/3/17
 * @since 1.0.0
 */
public class ClassPathXmlApplicationContext {

  private BeanFactory beanFactory;

  /**
   * 构造器获取外部配置，解析出Bean的定义，形成内存映像
   *
   * @param fileName 文件名称
   */
  public ClassPathXmlApplicationContext(String fileName) {
    Resource resource = new ClassPathXmlResource(fileName);
    this.beanFactory = new SimpleBeanFactory();
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
    reader.loadBeanDefinitions(resource);
  }

  public Object getBean(String beanName) throws BeansException {
    return this.beanFactory.getBean(beanName);
  }

  public void registerBeanDefinition(BeanDefinition beanDefinition) {
    this.beanFactory.registerBeanDefinition(beanDefinition);
  }
}
