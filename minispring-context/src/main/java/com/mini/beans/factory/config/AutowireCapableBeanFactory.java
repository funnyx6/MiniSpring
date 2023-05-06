package com.mini.beans.factory.config;

import com.mini.beans.BeansException;
import com.mini.beans.factory.support.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanxiao 2023/5/6
 * @since 1.0.0
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

  private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

  public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
    beanPostProcessors.remove(beanPostProcessor);
    beanPostProcessors.add(beanPostProcessor);
  }

  public int getBeanPostProcessorCount() {
    return this.beanPostProcessors.size();
  }

  public List<BeanPostProcessor> getBeanPostProcessors() {
    return this.beanPostProcessors;
  }

  @Override
  public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName)
      throws BeansException {
    Object result = existingBean;
    for (BeanPostProcessor processor : getBeanPostProcessors()) {
      Object current = processor.postProcessBeforeInitialization(result, beanName);
      if (current == null) {
        return result;
      }
      result = current;
    }
    return result;
  }

  @Override
  public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName)
      throws BeansException {
    Object result = existingBean;
    for (BeanPostProcessor processor : getBeanPostProcessors()) {
      Object current = processor.postProcessAfterInitialization(result, beanName);
      if (current == null) {
        return result;
      }
      result = current;
    }
    return result;
  }
}
