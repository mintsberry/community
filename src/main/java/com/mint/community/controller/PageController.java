package com.mint.community.controller;


import com.mint.community.mapper.UserMapper;
import com.mint.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PageController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = {"/","/index"})
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                String token = cookie.getValue();
                User user = userMapper.selByToken(token);
                if(user != null){
                    request.getSession().setAttribute("users", user);
                }
                break;
            }
        }
        return "index";
    }

    @RequestMapping("/{path}")
    public String page(@PathVariable String path){
        return path;
    }
}
