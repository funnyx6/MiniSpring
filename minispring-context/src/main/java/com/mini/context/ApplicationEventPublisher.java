package com.mini.context;

/**
 * @author fanxiao 2023/5/4
 * @since 1.0.0
 */
public interface ApplicationEventPublisher {

  void publishEvent(ApplicationEvent event);
}
