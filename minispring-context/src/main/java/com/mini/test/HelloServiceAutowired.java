package com.mini.test;

import com.mini.beans.factory.annotation.Autowired;

/**
 * @author fanxiao 2023/3/31
 * @since 1.0.0
 */
public class HelloServiceAutowired {

  @Autowired private HelloService1 helloService1;

  public void test() {
    helloService1.say();
  }
}
