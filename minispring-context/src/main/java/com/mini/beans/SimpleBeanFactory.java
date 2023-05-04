package com.mini.beans;

import com.mini.context.BeanDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fanxiao 2023/3/31
 * @since 1.0.0
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry
    implements BeanFactory, BeanDefinitionRegistry {

  private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
  private List<String> beanDefinitionNames = new ArrayList<>();

  @Override
  public Object getBean(String beanName) throws BeansException {
    Object bean = this.getSingleton(beanName);
    if (bean == null) {
      BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
      if (beanDefinition == null) {
        throw new BeansException("No Bean.");
      }

      try {
        bean = Class.forName(beanDefinition.getClassName()).newInstance();
      } catch (Exception e) {

      }
      this.registerSingleton(beanName, bean);
    }
    return bean;
  }

  //  @Override
  //  public void registerBeanDefinition(BeanDefinition beanDefinition) {
  //    this.beanDefinitionMap.put(beanDefinition.getId(), beanDefinition);
  //  }

  @Override
  public boolean containsBean(String beanName) {
    return containsSingleton(beanName);
  }

  //  @Override
  //  public void registerBean(String beanName, Object obj) {
  //    this.registerSingleton(beanName, obj);
  //  }

  @Override
  public Class getType(String name) {
    return this.beanDefinitionMap.get(name).getClass();
  }

  @Override
  public void registerBeanDefinition(String name, BeanDefinition bd) {
    this.beanDefinitionMap.put(name, bd);
    this.beanDefinitionNames.add(name);
    if (!bd.isLazyInit()) {
      try {
        getBean(name);
      } catch (BeansException e) {

      }
    }
  }

  @Override
  public void removeBeanDefinition(String name) {
    this.beanDefinitionMap.remove(name);
    this.beanDefinitionNames.remove(name);
    this.removeSingleton(name);
  }

  @Override
  public BeanDefinition getBeanDefinition(String name) {
    return this.beanDefinitionMap.get(name);
  }

  @Override
  public boolean containsBeanDefinition(String name) {
    return this.beanDefinitionMap.containsKey(name);
  }

  @Override
  public boolean isSingleton(String name) {
    return this.beanDefinitionMap.get(name).isSingleton();
  }

  @Override
  public boolean isPrototype(String name) {
    return this.beanDefinitionMap.get(name).isPrototype();
  }
}
