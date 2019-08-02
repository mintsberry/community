package com.mint.community.service;

import com.mint.community.enums.CommentTypeEnum;
import com.mint.community.exception.CustomizeStatusCode;
import com.mint.community.exception.CustomizeException;
import com.mint.community.mapper.CommentMapper;
import com.mint.community.mapper.QuestionMapper;
import com.mint.community.pojo.Comment;
import com.mint.community.pojo.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;
    public void insComment(Comment comment) {
        if (comment == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeStatusCode.TARGET_PARAM_NOT_FOUND);
        }
        if (!CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeStatusCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
            Comment selComment = commentMapper.selCommentById(comment.getParentId());
            if (selComment == null){
                throw new CustomizeException(CustomizeStatusCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insComment(comment);
        } else {
            Question question = questionMapper.selQueById(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeStatusCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insComment(comment);
            questionMapper.updCommentAdd(question.getId());
        }
    }
}
