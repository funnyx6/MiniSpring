package com.mini.context;

import java.util.EventObject;

/**
 * @author fanxiao 2023/5/4
 * @since 1.0.0
 */
public class ApplicationEvent extends EventObject {

  public ApplicationEvent(Object source) {
    super(source);
  }
}
