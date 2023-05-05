package com.mini.context;

/**
 * @author fanxiao 2023/5/4
 * @since 1.0.0
 */
public class PropertyValue {

  private String type;

  private String name;

  private Object value;

  private boolean isRef;

  public PropertyValue(String type, String name, Object value) {
    this.type = type;
    this.name = name;
    this.value = value;
  }

  public PropertyValue(String type, String name, Object value, boolean isRef) {
    this.type = type;
    this.name = name;
    this.value = value;
    this.isRef = isRef;
  }

  public PropertyValue(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public Object getValue() {
    return value;
  }

  public boolean isRef() {
    return isRef;
  }

  public void setRef(boolean ref) {
    isRef = ref;
  }
}
