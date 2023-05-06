package com.mini.beans.factory;

import com.mini.beans.BeansException;

/**
 * @author fanxiao 2023/5/6
 * @since 1.0.0
 */
public interface BeanFactoryAware {

  void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
