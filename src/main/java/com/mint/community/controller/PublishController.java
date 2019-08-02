package com.mint.community.controller;

import com.mint.community.mapper.QuestionMapper;
import com.mint.community.pojo.Question;
import com.mint.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable int id, HttpServletRequest request,
                       Model model){
        Question question = questionMapper.selQueById(id);
        User user = (User)(request.getSession().getAttribute("users"));
        if (user != null && user.getAccountId() == question.getCreator()){
            model.addAttribute("question",question);
            return "publish";
        } else {
            return "/";
        }

    }
    @PutMapping("/publish/{id}")
    public String updateQuestion(@PathVariable int id,HttpServletRequest request,
                                 String title,String description,String tag){
        Question question = questionMapper.selQueById(id);
        User user = (User)(request.getSession().getAttribute("users"));
        if (user != null && user.getAccountId() == question.getCreator()){
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.updArticleById(question);
            return "redirect:/question/" + id;
        } else {
            return "/";
        }
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
