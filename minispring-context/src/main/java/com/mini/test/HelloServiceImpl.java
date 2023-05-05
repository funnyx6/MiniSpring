package com.mini.test;

/**
 * @author fanxiao 2023/3/31
 * @since 1.0.0
 */
public class HelloServiceImpl implements HelloService {

  private String injectField;

  private HelloService1 helloService1;

  public String getInjectField() {
    return injectField;
  }

  public void setInjectField(String injectField) {
    this.injectField = injectField;
  }

  @Override
  public void say() {
    System.out.println("Hello..." + injectField);
  }

  public void setHelloService1(HelloService1 helloService1) {
    this.helloService1 = helloService1;
  }

  public HelloService1 getHelloService1() {
    return helloService1;
  }
}
