package com.mini.beans.factory.xml;

import com.mini.beans.factory.config.ArgumentValue;
import com.mini.beans.factory.config.ArgumentValues;
import com.mini.beans.factory.config.AutowireCapableBeanFactory;
import com.mini.beans.factory.config.BeanDefinition;
import com.mini.context.PropertyValue;
import com.mini.context.PropertyValues;
import com.mini.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanxiao 2023/3/31
 * @since 1.0.0
 */
public class XmlBeanDefinitionReader {

  private AutowireCapableBeanFactory beanFactory;

  public XmlBeanDefinitionReader(AutowireCapableBeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  public void loadBeanDefinitions(Resource resource) {
    while (resource.hasNext()) {
      Element element = (Element) resource.next();
      String beanId = element.attributeValue("id");
      String beanClass = element.attributeValue("class");
      String lazyInit = element.attributeValue("lazyInit");
      BeanDefinition beanDefinition =
          new BeanDefinition(beanId, beanClass, Boolean.valueOf(lazyInit));

      // 处理属性参数
      List<Element> propertyElements = element.elements("property");
      List<String> refs = new ArrayList<>();

      PropertyValues propertyValues = new PropertyValues();
      for (Element propertyElement : propertyElements) {
        String type = propertyElement.attributeValue("type");
        String name = propertyElement.attributeValue("name");
        String value = propertyElement.attributeValue("value");
        String ref = propertyElement.attributeValue("ref");
        String pV = "";
        boolean isRef = false;
        if ((value != null && !"".equals(value))) {
          pV = value;
          isRef = false;
        } else if (ref != null && !"".equals(ref)) {
          pV = ref;
          isRef = true;
          refs.add(ref);
        }

        propertyValues.addPropertyValue(new PropertyValue(type, name, pV, isRef));
      }
      beanDefinition.setDependsOn(refs.toArray(new String[0]));
      beanDefinition.setPropertyValues(propertyValues);

      // 处理构造器参数
      List<Element> constructorElements = element.elements("constructor-arg");
      ArgumentValues argumentValues = new ArgumentValues();
      for (Element constructorElement : constructorElements) {
        String type = constructorElement.attributeValue("type");
        String name = constructorElement.attributeValue("name");
        String value = constructorElement.attributeValue("value");
        argumentValues.addGenericArgumentValue(new ArgumentValue(value, type, name));
      }

      beanDefinition.setConstructorArgumentValues(argumentValues);

      beanFactory.registerBeanDefinition(beanId, beanDefinition);
    }
  }
}
