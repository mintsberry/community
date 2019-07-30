package com.mint.community.controller;

import com.mint.community.mapper.QuestionMapper;
import com.mint.community.pojo.Question;
import com.mint.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String addQuestion(Question question,
                              HttpSession session,
                              Model model){
        Object users = session.getAttribute("users");
        String message;

        if (users != null) {
            User user = (User) users;
            question.setCreator(user.getAccountId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insQue(question);
            return "redirect:/index";
        } else {
            message = "用户未登录";
            model.addAttribute("msg", message);
        }
        return "publish";
    }
}
