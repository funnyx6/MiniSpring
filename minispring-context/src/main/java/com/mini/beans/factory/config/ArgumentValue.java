package com.mini.beans.factory.config;

/**
 * @author fanxiao 2023/5/4
 * @since 1.0.0
 */
public class ArgumentValue {

  private Object value;

  private String type;

  private String name;

  public ArgumentValue(Object value, String type) {
    this.value = value;
    this.type = type;
  }

  public ArgumentValue(Object value, String type, String name) {
    this.value = value;
    this.type = type;
    this.name = name;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
