package com.mini.test;

/**
 * @author fanxiao 2023/3/31
 * @since 1.0.0
 */
public class HelloServiceImpl implements HelloService {

  private String injectField;

  public String getInjectField() {
    return injectField;
  }

  public void setInjectField(String injectField) {
    this.injectField = injectField;
  }

  @Override
  public void say() {
    System.out.println("Hello...");
  }
}
