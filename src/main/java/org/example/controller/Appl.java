package org.example.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.springConfig.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Setter
@Getter
public class Appl {
    public static ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
}
