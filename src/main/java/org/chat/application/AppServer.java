package org.chat.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * @author Saransh Gupta
 */
@SpringBootApplication
@ComponentScan
public class AppServer {

    public static void main(String args[])
    {
        SpringApplication.run(AppServer.class, args);
        System.out.println("Server Started!");
    }
}
