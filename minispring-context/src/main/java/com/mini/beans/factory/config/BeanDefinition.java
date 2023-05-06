package com.mini.beans.factory.config;

import com.mini.context.PropertyValues;

/**
 * @author fanxiao 2023/3/17
 * @since 1.0.0
 */
public class BeanDefinition {

  String SCOPE_SINGLETON = "singleton";
  String SCOPE_PROTOTYPE = "prototype";
  private boolean lazyInit = false;
  private String[] dependsOn;
  private ArgumentValues constructorArgumentValues;
  private PropertyValues propertyValues;
  private String initMethodName;
  private volatile Object beanClass;
  private String scope = SCOPE_SINGLETON;

  private String id;

  private String className;

  public BeanDefinition(String id, String className) {
    this.id = id;
    this.className = className;
  }

  public BeanDefinition(String id, String className, boolean lazyInit) {
    this.id = id;
    this.className = className;
    this.lazyInit = lazyInit;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public boolean isLazyInit() {
    return lazyInit;
  }

  public void setLazyInit(boolean lazyInit) {
    this.lazyInit = lazyInit;
  }

  public String[] getDependsOn() {
    return dependsOn;
  }

  public void setDependsOn(String[] dependsOn) {
    this.dependsOn = dependsOn;
  }

  public ArgumentValues getConstructorArgumentValues() {
    return constructorArgumentValues;
  }

  public void setConstructorArgumentValues(ArgumentValues constructorArgumentValues) {
    this.constructorArgumentValues = constructorArgumentValues;
  }

  public PropertyValues getPropertyValues() {
    return propertyValues;
  }

  public void setPropertyValues(PropertyValues propertyValues) {
    this.propertyValues = propertyValues;
  }

  public String getInitMethodName() {
    return initMethodName;
  }

  public void setInitMethodName(String initMethodName) {
    this.initMethodName = initMethodName;
  }

  public Object getBeanClass() {
    try {
      return beanClass == null ? Class.forName(className) : beanClass;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return beanClass;
  }

  public void setBeanClass(Object beanClass) {
    this.beanClass = beanClass;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public boolean isSingleton() {
    return SCOPE_SINGLETON.equals(scope);
  }

  public boolean isPrototype() {
    return SCOPE_PROTOTYPE.equals(scope);
  }
}
