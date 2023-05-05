package com.mini.test;

/**
 * @author fanxiao 2023/3/31
 * @since 1.0.0
 */
public class HelloService1 {

  private HelloService helloService;

  public void say() {
    System.out.println("HelloService1...");
  }

  public HelloService getHelloService() {
    return helloService;
  }

  public void setHelloService(HelloService helloService) {
    this.helloService = helloService;
  }
}
