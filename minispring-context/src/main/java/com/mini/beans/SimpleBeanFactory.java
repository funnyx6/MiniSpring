package com.mini.beans;

import com.mini.context.BeanDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fanxiao 2023/3/31
 * @since 1.0.0
 */
public class SimpleBeanFactory implements BeanFactory {
  private List<BeanDefinition> beanDefinitions = new ArrayList<>();

  private List<String> beanNames = new ArrayList<>();

  private Map<String, Object> singletons = new HashMap<>();

  @Override
  public Object getBean(String beanName) throws BeansException {
    Object bean = singletons.get(beanName);
    if (bean == null) {
      int i = beanNames.indexOf(beanName);
      if (i == -1) {
        throw new BeansException();
      } else {
        BeanDefinition beanDefinition = beanDefinitions.get(i);
        try {
          bean = Class.forName(beanDefinition.getClassName()).newInstance();
          singletons.put(beanDefinition.getId(), bean);
        } catch (Exception e) {

        }
      }
    }

    return bean;
  }

  @Override
  public void registerBeanDefinition(BeanDefinition beanDefinition) {
    this.beanDefinitions.add(beanDefinition);
    this.beanNames.add(beanDefinition.getId());
  }
}
