package com.example.jiuYe2.controller;

import com.example.jiuYe2.service.RegLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/reglog")
public class RegLogController {

    @Resource
    RegLogService regLogService;

    @RequestMapping("/page")
    @ResponseBody
    public String getLoginPage(){
        return regLogService.getLoginPage();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Model model, HttpServletResponse response, @RequestParam(value = "recall", required = false) String recall,
                           @RequestParam String name, @RequestParam String password) {
        Map<String, String> map = regLogService.register(name, password);
        if (map.containsKey("msg")) {
            // 向前端返回信息。
            model.addAttribute("msg", map.get("msg"));
            return "/reglog/page";
        }
        Cookie cookie = new Cookie("ticket", map.get("ticket"));
        cookie.setPath("/");
        response.addCookie(cookie);
        if (recall != null) {
            return "redirect:" + recall;
        }
        return "redirect:/user/page";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, HttpServletResponse response, @RequestParam(value = "recall", required = false) String recall,
                        @RequestParam String name, @RequestParam String password) {
        Map<String, String> map = regLogService.login(name, password);
        if (map.containsKey("msg")) {
            model.addAttribute("msg", map.get("msg"));
            return "/reglog/page";
        }
        Cookie cookie = new Cookie("ticket", map.get("ticket"));
        cookie.setPath("/");
        response.addCookie(cookie);
        if (recall != null) {
            return "redirect:" + recall;
        }
        return "redirect:/user/page";
    }

    @RequestMapping("/logout")
    public String logout(@CookieValue String ticket) {
        regLogService.logout(ticket);
        return "redirect:/index";
    }

}
