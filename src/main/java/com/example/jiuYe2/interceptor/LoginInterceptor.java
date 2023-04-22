package com.example.jiuYe2.interceptor;

import com.example.jiuYe2.util.HostHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (hostHolder.getUser() == null) {
            response.sendRedirect("/reglog/page?recall=" + request.getRequestURI());
            // 因为重定向相当于二次请求，所以要返回false废弃之前的请求
            return false;
        }
        return true;
    }

}
