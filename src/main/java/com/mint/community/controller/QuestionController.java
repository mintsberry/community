package com.mint.community.controller;

import com.mint.community.dto.CommentDTO;
import com.mint.community.dto.QuestionDTO;
import com.mint.community.enums.CommentTypeEnum;
import com.mint.community.pojo.Question;
import com.mint.community.service.CommentService;
import com.mint.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable int id,
                           Model model){
        QuestionDTO questionDTO = questionService.selQueById(id);
        questionService.incReaderCount(id);
        List<CommentDTO> commentDTOS = commentService.selCommentByParentIdAndType(id, CommentTypeEnum.QUESTION);
        List<Question> relevantTopics = questionService.selRelevantTopics(questionDTO);
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("commentDTOS",commentDTOS);
        model.addAttribute("relevantTopics",relevantTopics);
        return "question";
    }
}
