package com.mini.context;

import com.mini.beans.BeanFactory;
import com.mini.beans.BeansException;
import com.mini.beans.SimpleBeanFactory;
import com.mini.beans.XmlBeanDefinitionReader;
import com.mini.core.ClassPathXmlResource;
import com.mini.core.Resource;

/**
 * 解析 XML 文件中的内容。
 *
 * <p>加载解析的内容， 构建 BeanDefinition。
 *
 * <p>读取 BeanDefinition 的配置信息，实例化 Bean，然后把它注入到 BeanFactory 容器中。
 *
 * @author fanxiao 2023/3/17
 * @since 1.0.0
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

  private BeanFactory beanFactory;

  /**
   * 构造器获取外部配置，解析出Bean的定义，形成内存映像
   *
   * @param fileName 文件名称
   */
  public ClassPathXmlApplicationContext(String fileName) {
    Resource resource = new ClassPathXmlResource(fileName);
    this.beanFactory = new SimpleBeanFactory();
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader((SimpleBeanFactory) beanFactory);
    reader.loadBeanDefinitions(resource);
  }

  @Override
  public Object getBean(String beanName) throws BeansException {
    return this.beanFactory.getBean(beanName);
  }

  //  @Override
  //  public void registerBeanDefinition(BeanDefinition beanDefinition) {
  //    this.beanFactory.registerBeanDefinition(beanDefinition);
  //  }

  @Override
  public boolean containsBean(String beanName) {
    return this.beanFactory.containsBean(beanName);
  }

  //  @Override
  //  public void registerBean(String beanName, Object obj) {
  //    this.beanFactory.registerBean(beanName, obj);
  //  }

  @Override
  public boolean isSingleton(String name) {
    return false;
  }

  @Override
  public boolean isPrototype(String name) {
    return false;
  }

  @Override
  public Class getType(String name) {
    return null;
  }

  @Override
  public void publishEvent(ApplicationEvent event) {}
}
