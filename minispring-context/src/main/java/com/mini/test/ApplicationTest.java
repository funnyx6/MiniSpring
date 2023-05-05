package com.mini.test;

import com.mini.beans.BeansException;
import com.mini.context.ClassPathXmlApplicationContext;

/**
 * @author fanxiao 2023/3/31
 * @since 1.0.0
 */
public class ApplicationTest {

  public static void main(String[] args) throws BeansException {
    //
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("application.xml");

    //    HelloService helloService = (HelloService) applicationContext.getBean("helloService");
    //    helloService.say();

    HelloServiceImpl helloServiceInject =
        (HelloServiceImpl) applicationContext.getBean("helloServiceInject");
    //    helloServiceInject.say();
    helloServiceInject.getHelloService1().say();

    HelloService1 helloService1 = (HelloService1) applicationContext.getBean("helloService1");
    helloService1.getHelloService().say();
  }
}
