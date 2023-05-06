package com.mini.beans.factory.annotation;

import com.mini.beans.BeansException;
import com.mini.beans.factory.BeanFactory;
import com.mini.beans.factory.BeanFactoryAware;
import com.mini.beans.factory.config.AutowireCapableBeanFactory;
import com.mini.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * @author fanxiao 2023/5/6
 * @since 1.0.0
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

  private AutowireCapableBeanFactory beanFactory;

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    Object result = bean;
    Class<?> clz = bean.getClass();
    Field[] declaredFields = clz.getDeclaredFields();
    if (declaredFields != null && declaredFields.length > 0) {
      for (Field field : declaredFields) {
        if (field.isAnnotationPresent(Autowired.class)) {
          Object autowiredCandidate = this.beanFactory.getBean(field.getName());
          field.setAccessible(true);
          try {
            field.set(result, autowiredCandidate);
            System.out.println("autowire " + field.getName() + " for bean " + beanName);
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        }
      }
    }

    return result;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    return null;
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = (AutowireCapableBeanFactory) beanFactory;
  }
}
