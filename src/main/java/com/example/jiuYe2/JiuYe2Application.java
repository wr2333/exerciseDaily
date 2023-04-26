package com.example.jiuYe2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class JiuYe2Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(JiuYe2Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(JiuYe2Application.class);
    }

}
