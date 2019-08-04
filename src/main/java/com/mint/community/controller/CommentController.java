package com.mint.community.controller;

import com.mint.community.dto.CommentDTO;
import com.mint.community.dto.ResultDTO;
import com.mint.community.enums.CommentTypeEnum;
import com.mint.community.exception.CustomizeStatusCode;
import com.mint.community.pojo.Comment;
import com.mint.community.mapper.CommentMapper;
import com.mint.community.pojo.User;
import com.mint.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.List;

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
        } else if(comment.getContent() == null || comment.getContent().equals("")){
            resultDTO.setStatus(CustomizeStatusCode.COMMENT_IS_EMPTY);
        } else {
            comment.setGmtModified(System.currentTimeMillis());
            comment.setGmtCreate(System.currentTimeMillis());
            comment.setCommentator(user.getAccountId());
            commentService.insComment(comment, user);
            resultDTO.setStatus(CustomizeStatusCode.SUCCESS);
            resultDTO.setData(comment);
        }
        return resultDTO;

    }
    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDTO comment(@PathVariable int id){
        List<CommentDTO> commentDTOS = commentService.selCommentByParentIdAndType(id, CommentTypeEnum.COMMENT);
        ResultDTO<List<CommentDTO>> resultDTO = new ResultDTO<>();
        resultDTO.setData(commentDTOS);
        resultDTO.setStatus(CustomizeStatusCode.SUCCESS);
        return resultDTO;
    }
}
