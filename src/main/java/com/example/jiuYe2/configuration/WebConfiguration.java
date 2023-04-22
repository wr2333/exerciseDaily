package com.example.jiuYe2.configuration;

import com.example.jiuYe2.interceptor.LoginInterceptor;
import com.example.jiuYe2.interceptor.PassportInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Component
public class WebConfiguration implements WebMvcConfigurer {

    @Resource
    PassportInterceptor passportInterceptor;

    @Resource
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 按拦截器运行顺序添加
        registry.addInterceptor(passportInterceptor);
        // 登录拦截器放行登录注册页面
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/reglog/*", "/error/*");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
