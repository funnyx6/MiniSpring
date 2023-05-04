package com.mini.context;

/**
 * @author fanxiao 2023/5/4
 * @since 1.0.0
 */
public class PropertyValue {

  private final String name;

  private final Object value;

  public PropertyValue(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public Object getValue() {
    return value;
  }
}
