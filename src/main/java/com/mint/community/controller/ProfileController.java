package com.mint.community.controller;

import com.mint.community.dto.PaginationDTO;
import com.mint.community.pojo.User;
import com.mint.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, HttpSession session,
                          Model model, @RequestParam(name = "page", defaultValue = "1") int page,
                          @RequestParam(name = "size", defaultValue = "5") int size){
        if ("questions".equals(action)){
            model.addAttribute("section", action);
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO paginationDTO = questionService.selListQue(page, size);
            model.addAttribute("pagination", paginationDTO);
            Object users = session.getAttribute("users");
            if (users != null) {
                User user = (User) users;
                PaginationDTO pagination = questionService.selListQueByUserId(user.getAccountId(), page, size);
                model.addAttribute("pagination", pagination);
            }
        } else if ("replies".equals(action)){
            model.addAttribute("section", action);
            model.addAttribute("sectionName", "我的回复");
        }

        return "profile";
    }
}
