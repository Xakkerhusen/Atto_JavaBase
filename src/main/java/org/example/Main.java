package org.example;

import org.example.controller.Controllers;
import org.example.db.DatabaseUtil;
import org.example.springConfig.SpringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(SpringConfig.class);
        Controllers controllers = applicationContext.getBean("controllers", Controllers.class);
        controllers.start();
    }
}