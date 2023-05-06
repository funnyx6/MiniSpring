package com.mini.beans.factory.support;

import com.mini.beans.factory.config.BeanDefinition;

/**
 * 集中存放 {@link BeanDefinition}
 *
 * @author fanxiao 2023/5/4
 * @since 1.0.0
 */
public interface BeanDefinitionRegistry {

  void registerBeanDefinition(String name, BeanDefinition bd);

  void removeBeanDefinition(String name);

  BeanDefinition getBeanDefinition(String name);

  boolean containsBeanDefinition(String name);
}
