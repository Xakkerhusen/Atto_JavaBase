package org.example;

import org.example.controller.Appl;
import org.example.controller.Controller;

public class Main {
    public static void main(String[] args) {
/*       ApplicationContext applicationContext = new ClassPathXmlApplicationContext("test.xml");
        Controller controller= (Controller) applicationContext.getBean("controller");
//spring da tajriba oxshamadi*/


//        Controller controller=new Controller();

        Controller controller= Appl.applicationContext.getBean("controller",Controller.class);
        controller.start();

    }
}