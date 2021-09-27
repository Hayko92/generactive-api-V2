package com.example.generative_api_v2.conf;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContext {
    public static final AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(Config.class);
}
