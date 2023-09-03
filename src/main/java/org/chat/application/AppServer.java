package org.chat.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * @author Saransh Gupta
 */
@SpringBootApplication
@ComponentScan
public class AppServer {

    private static final Logger logger = LoggerFactory.getLogger(AppServer.class);
    public static void main(String args[])
    {
        SpringApplication.run(AppServer.class, args);
        logger.info("Server Started!");
    }
}
