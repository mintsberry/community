package com.mint.community.controller;

import com.mint.community.dto.QuestionDTO;
import com.mint.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable int id,
                           Model model){
        QuestionDTO question = questionService.selQueById(id);
        questionService.incReaderCount(id);
        model.addAttribute("question", question);
        return "question";
    }
}
