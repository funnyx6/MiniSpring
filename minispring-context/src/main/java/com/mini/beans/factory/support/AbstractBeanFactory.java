package com.mini.beans.factory.support;

import com.mini.beans.BeansException;
import com.mini.beans.factory.BeanFactory;
import com.mini.beans.factory.BeanFactoryAware;
import com.mini.beans.factory.config.ArgumentValue;
import com.mini.beans.factory.config.ArgumentValues;
import com.mini.beans.factory.config.BeanDefinition;
import com.mini.context.PropertyValue;
import com.mini.context.PropertyValues;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fanxiao 2023/5/6
 * @since 1.0.0
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry
    implements BeanFactory, BeanDefinitionRegistry {

  private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
  private List<String> beanDefinitionNames = new ArrayList<>();
  private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

  public AbstractBeanFactory() {}

  public void refresh() {
    for (String beanDefinitionName : beanDefinitionNames) {
      try {
        BeanDefinition beanDefinition = getBeanDefinition(beanDefinitionName);
        if (!beanDefinition.isLazyInit()) {
          getBean(beanDefinitionName);
        }
      } catch (BeansException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public Object getBean(String beanName) throws BeansException {
    Object bean = this.getSingleton(beanName);
    if (bean == null) {
      bean = this.earlySingletonObjects.get(beanName);

      if (bean == null) {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
          throw new BeansException("No Bean:" + beanName);
        }

        try {
          bean = createBean(beanDefinition);
        } catch (Exception e) {

        }
        this.registerSingleton(beanName, bean);

        initializeBean(beanDefinition, bean);
      }
    }
    return bean;
  }

  public <T> T getBean(Class<T> requiredType) {
    Object bean = null;
    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition = getBeanDefinition(beanDefinitionName);
      if (beanDefinition != null) {
        if (requiredType.isAssignableFrom((Class<?>) beanDefinition.getBeanClass())) {
          try {
            bean = getBean(beanDefinition.getId());
          } catch (BeansException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return (T) bean;
  }

  private void initializeBean(BeanDefinition beanDefinition, Object bean) throws BeansException {

    invokeAwareMethods(beanDefinition.getId(), bean);

    // 进行beanpostprocessor处理
    applyBeanPostProcessorBeforeInitialization(bean, beanDefinition.getId());

    if (beanDefinition.getInitMethodName() != null
        && !beanDefinition.getInitMethodName().equals("")) {
      invokeInitMethod(beanDefinition, bean);
    }

    applyBeanPostProcessorAfterInitialization(bean, beanDefinition.getId());
  }

  private void invokeAwareMethods(String beanName, Object bean) throws BeansException {
    if (bean instanceof BeanFactoryAware) {
      ((BeanFactoryAware) bean).setBeanFactory(AbstractBeanFactory.this);
    }
  }

  @Override
  public boolean containsBean(String beanName) {
    return containsSingleton(beanName);
  }

  @Override
  public boolean isSingleton(String name) {
    return this.beanDefinitionMap.get(name).isSingleton();
  }

  @Override
  public boolean isPrototype(String name) {
    return this.beanDefinitionMap.get(name).isPrototype();
  }

  @Override
  public Class getType(String name) {
    Object singleton = getSingleton(name);
    if (singleton != null) {
      return singleton.getClass();
    }
    return (Class) beanDefinitionMap.get(name).getBeanClass();
  }

  @Override
  public void registerBeanDefinition(String name, BeanDefinition bd) {
    this.beanDefinitionMap.put(name, bd);
    this.beanDefinitionNames.add(name);
  }

  @Override
  public void removeBeanDefinition(String name) {
    this.beanDefinitionMap.remove(name);
    this.beanDefinitionNames.remove(name);
    this.removeSingleton(name);
  }

  @Override
  public BeanDefinition getBeanDefinition(String name) {
    return beanDefinitionMap.get(name);
  }

  @Override
  public boolean containsBeanDefinition(String name) {
    return beanDefinitionMap.containsKey(name);
  }

  private void invokeInitMethod(BeanDefinition bd, Object bean) {
    Class<?> clz = bean.getClass();
    Method method = null;
    try {
      method = clz.getMethod(bd.getInitMethodName());
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    try {
      method.invoke(bean);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  private Object createBean(BeanDefinition bd) {
    Class<?> clz = null;
    Object obj = doCreateBean(bd);
    this.earlySingletonObjects.put(bd.getId(), obj);
    try {
      clz = Class.forName(bd.getClassName());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    populateBean(bd, clz, obj);
    return obj;
  }

  private Object doCreateBean(BeanDefinition bd) {

    Class<?> clz = null;
    Object obj = null;
    Constructor<?> con;

    try {
      clz = Class.forName(bd.getClassName());

      // handle constructor
      ArgumentValues argumentValues = bd.getConstructorArgumentValues();
      if (argumentValues != null && !argumentValues.isEmpty()) {
        Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
        Object[] paramValues = new Object[argumentValues.getArgumentCount()];
        for (int i = 0; i < argumentValues.getArgumentCount(); i++) {
          ArgumentValue argumentValue = argumentValues.getIndexedArgumentValue(i);
          if ("String".equals(argumentValue.getType())
              || "java.lang.String".equals(argumentValue.getType())) {
            paramTypes[i] = String.class;
            paramValues[i] = argumentValue.getValue();
          } else if ("Integer".equals(argumentValue.getType())
              || "java.lang.Integer".equals(argumentValue.getType())) {
            paramTypes[i] = Integer.class;
            paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
          } else if ("int".equals(argumentValue.getType())) {
            paramTypes[i] = int.class;
            paramValues[i] = Integer.valueOf((String) argumentValue.getValue()).intValue();
          } else {
            paramTypes[i] = String.class;
            paramValues[i] = argumentValue.getValue();
          }
        }
        try {
          con = clz.getConstructor(paramTypes);
          obj = con.newInstance(paramValues);
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
        } catch (SecurityException e) {
          e.printStackTrace();
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
      } else {
        obj = clz.newInstance();
      }

    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return obj;
  }

  private void populateBean(BeanDefinition beanDefinition, Class clz, Object obj) {
    handleProperties(beanDefinition, clz, obj);
  }

  private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj) {
    // 处理属性
    System.out.println("handle properties for bean : " + bd.getId());
    PropertyValues propertyValues = bd.getPropertyValues();
    // 如果有属性
    if (propertyValues != null && !propertyValues.isEmpty()) {
      for (int i = 0; i < propertyValues.size(); i++) {
        PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
        String pName = propertyValue.getName();
        String pType = propertyValue.getType();
        Object pValue = propertyValue.getValue();
        boolean isRef = propertyValue.isRef();
        Class<?>[] paramTypes = new Class<?>[1];
        Object[] paramValues = new Object[1];
        if (!isRef) { // 如果不是ref，只是普通属性
          // 对每一个属性，分数据类型分别处理
          if ("String".equals(pType) || "java.lang.String".equals(pType)) {
            paramTypes[0] = String.class;
          } else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
            paramTypes[0] = Integer.class;
          } else if ("int".equals(pType)) {
            paramTypes[0] = int.class;
          } else {
            paramTypes[0] = String.class;
          }

          paramValues[0] = pValue;
        } else { // is ref, create the dependent beans
          try {
            paramTypes[0] = Class.forName(pType);
          } catch (ClassNotFoundException e) {
            e.printStackTrace();
          }
          try {
            // 再次调用getBean创建ref的bean实例
            paramValues[0] = getBean((String) pValue);
          } catch (BeansException e) {
            e.printStackTrace();
          }
        }

        // 按照setXxxx规范查找setter方法，调用setter方法设置属性
        String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
        Method method = null;
        try {
          method = clz.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
        }
        try {
          method.invoke(obj, paramValues);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public abstract Object applyBeanPostProcessorBeforeInitialization(
      Object existingBean, String beanName) throws BeansException;

  public abstract Object applyBeanPostProcessorAfterInitialization(
      Object existingBean, String beanName) throws BeansException;
}
