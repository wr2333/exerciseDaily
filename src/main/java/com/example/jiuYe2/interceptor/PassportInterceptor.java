package com.example.jiuYe2.interceptor;

import com.example.jiuYe2.dao.LoginTicketDAO;
import com.example.jiuYe2.dao.UserDAO;
import com.example.jiuYe2.model.LoginTicket;
import com.example.jiuYe2.model.User;
import com.example.jiuYe2.util.HostHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Resource
    LoginTicketDAO loginTicketDAO;

    @Resource
    UserDAO userDAO;

    @Resource
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        if (request.getCookies() != null){
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        // 判断用户是否登录过
        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDAO.getTicketByTicket(ticket);
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                User user = userDAO.selectById(loginTicket.getUserId());
                // ticket未过期，加入本地线程
                hostHolder.setUser(user);
            }
        }
        // 用户没登录过或ticket过期，不加入本地线程
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null){
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
