package com.mini.beans.factory.config;

import com.mini.beans.BeansException;

/**
 * @author fanxiao 2023/5/6
 * @since 1.0.0
 */
public interface BeanPostProcessor {

  Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

  Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
