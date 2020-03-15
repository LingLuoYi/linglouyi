package com.linglouyi.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BlogApplication {

    public static ConfigurableApplicationContext ac;

    public static void main(String[] args) {
         ac = SpringApplication.run(BlogApplication.class,args);
    }

}
