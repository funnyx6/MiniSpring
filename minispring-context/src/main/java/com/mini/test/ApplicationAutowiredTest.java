package com.mini.test;

import com.mini.beans.BeansException;
import com.mini.context.ClassPathXmlApplicationContext;

/**
 * @author fanxiao 2023/3/31
 * @since 1.0.0
 */
public class ApplicationAutowiredTest {

  public static void main(String[] args) throws BeansException {
    //
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("application-autowired.xml");

    HelloServiceAutowired helloServiceAutowired =
        (HelloServiceAutowired) applicationContext.getBean("helloServiceAutowired");
    helloServiceAutowired.test();
  }
}
