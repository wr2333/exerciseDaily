package com.example.jiuYe2.configuration;

import com.example.jiuYe2.interceptor.PassportInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Component
public class WebConfiguration implements WebMvcConfigurer {

    @Resource
    PassportInterceptor passportInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
