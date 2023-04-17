package com.example.jiuYe2.controller;

import com.example.jiuYe2.service.IndexService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Controller
public class IndexController {

    @Resource
    private IndexService indexService;

    @RequestMapping(path = {"/index/{id}", "/index"})
    @ResponseBody
    public String index(@PathVariable(value = "id",required = false) Long id, @RequestParam(value = "page", defaultValue = "1") Long page,
                        HttpSession session) {
        if (id == null) id = 3L;
        return String.format(session.getAttribute("msg") + "hello, dear user %d, the page is %d", id, page);
    }

    @RequestMapping("/rrs")
    @ResponseBody
    public String rrs(HttpServletRequest request, HttpServletResponse response,
                      HttpSession session, @CookieValue("buySession") String sessionId) {
        StringBuilder sb = new StringBuilder();
        sb.append("请求类型为：<br/>");
        sb.append(request.getMethod() + "<br/>");
        sb.append("所有Header为：<br/>");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name + ": " + request.getHeader(name) + "<br/>");
        }
        sb.append("sessionId为：<br/>");
        sb.append("sessionId: " + sessionId);

        response.addHeader("userId", "233");
        response.addCookie(new Cookie("buySession", "1234"));

        session.setAttribute("msg", "传递请求。");
        return sb.toString();
    }

    @RequestMapping("/content")
    @ResponseBody
    public String getContent(){
        return indexService.getContent();
    }

    @RequestMapping(path = "/")
    public String redirect(HttpSession session) {
        session.setAttribute("msg", "跳转而来。");
        return "redirect:/index";
    }

    @ExceptionHandler()
    @ResponseBody
    public String errorHanlder(Exception e){
        return "错误为：" + e.getMessage();
    }

}
