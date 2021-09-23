package com.example.generative_api_v2.conf;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContext {
  static final   ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");
}
