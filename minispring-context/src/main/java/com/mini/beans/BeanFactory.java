package com.mini.beans;

import com.mini.context.BeanDefinition;

/**
 * bean工厂
 *
 * @author fanxiao
 * @since 1.0.0
 * @date 2023/03/31
 */
public interface BeanFactory {

  Object getBean(String beanName) throws BeansException;

  void registerBeanDefinition(BeanDefinition beanDefinition);
}
