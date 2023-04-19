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
        if (ticket != null) {   //用户登录过
            LoginTicket loginTicket = loginTicketDAO.getTicketByTicket(ticket);
            if (loginTicket == null || loginTicket.getStatus() == 1 || loginTicket.getExpired().before(new Date())) {
                return true;    //ticket过期，需要登录
            }
            User user = userDAO.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return true;    //用户没登录过，需要登录
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
