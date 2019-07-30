package com.mint.community.controller;


import com.mint.community.dto.QuestionDTO;
import com.mint.community.mapper.QuestionMapper;
import com.mint.community.mapper.UserMapper;
import com.mint.community.pojo.Question;
import com.mint.community.pojo.User;
import com.mint.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = {"/","/index"})
    public String index(HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
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
        }
        List<QuestionDTO> questionList = questionService.insListQue();
        model.addAttribute("questions",questionList);
        return "index";
    }

    @RequestMapping("/{path}")
    public String page(@PathVariable String path){
        return path;
    }
}
