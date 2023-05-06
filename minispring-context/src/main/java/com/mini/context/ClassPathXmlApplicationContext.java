package com.mini.context;

import com.mini.beans.BeansException;
import com.mini.beans.factory.BeanFactory;
import com.mini.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.mini.beans.factory.config.AutowireCapableBeanFactory;
import com.mini.beans.factory.config.BeanDefinition;
import com.mini.beans.factory.config.BeanPostProcessor;
import com.mini.beans.factory.xml.XmlBeanDefinitionReader;
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

  private AutowireCapableBeanFactory beanFactory;

  public ClassPathXmlApplicationContext(String fileName) {
    this(fileName, true);
  }

  public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
    Resource resource = new ClassPathXmlResource(fileName);
    this.beanFactory = new AutowireCapableBeanFactory();
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
    reader.loadBeanDefinitions(resource);
    if (isRefresh) {
      refresh();
    }
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

  public void refresh() {

    registerBeanPostProcessors(beanFactory);

    onRefresh();
  }

  private void registerBeanPostProcessors(AutowireCapableBeanFactory beanFactory) {
    beanFactory.registerBeanDefinition(
        "autowiredAnnotationBeanPostProcessor",
        new BeanDefinition(
            "autowiredAnnotationBeanPostProcessor",
            AutowiredAnnotationBeanPostProcessor.class.getName()));
    BeanPostProcessor beanPostProcessor = beanFactory.getBean(BeanPostProcessor.class);

    beanFactory.addBeanPostProcessor(beanPostProcessor);
  }

  private void onRefresh() {
    this.beanFactory.refresh();
  }
}
