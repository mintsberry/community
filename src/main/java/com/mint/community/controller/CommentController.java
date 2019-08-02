package com.mint.community.controller;

import com.mint.community.dto.ResultDTO;
import com.mint.community.exception.CustomizeStatusCode;
import com.mint.community.pojo.Comment;
import com.mint.community.mapper.CommentMapper;
import com.mint.community.pojo.User;
import com.mint.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public ResultDTO<Comment> addComment(@RequestBody Comment comment, HttpSession session){

        User user = (User)(session.getAttribute("users"));
        ResultDTO<Comment> resultDTO = new ResultDTO<>();
        if (user == null){
            resultDTO.setStatus(CustomizeStatusCode.ACCOUNT_NOT_LOGIN);
        } else {
            comment.setGmtModified(System.currentTimeMillis());
            comment.setGmtCreate(System.currentTimeMillis());
            comment.setCommentator(1);
            commentService.insComment(comment);
            resultDTO.setStatus(CustomizeStatusCode.SUCCESS);
            resultDTO.setData(comment);
        }
        return resultDTO;

    }
}
