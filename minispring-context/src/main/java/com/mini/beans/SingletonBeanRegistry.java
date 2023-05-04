package com.mini.beans;

/**
 * @author fanxiao 2023/4/20
 * @since 1.0.0
 */
public interface SingletonBeanRegistry {

  void registerSingleton(String beanName, Object singletonObject);

  Object getSingleton(String beanName);

  boolean containsSingleton(String beanName);

  String[] getSingletonNames();

  void removeSingleton(String beanName);
}
