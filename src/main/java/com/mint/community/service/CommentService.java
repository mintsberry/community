package com.mint.community.service;

import com.mint.community.dto.CommentDTO;
import com.mint.community.enums.CommentTypeEnum;
import com.mint.community.enums.NotificationEnum;
import com.mint.community.enums.NotificationStatusEnum;
import com.mint.community.exception.CustomizeStatusCode;
import com.mint.community.exception.CustomizeException;
import com.mint.community.mapper.CommentMapper;
import com.mint.community.mapper.NotificationMapper;
import com.mint.community.mapper.QuestionMapper;
import com.mint.community.mapper.UserMapper;
import com.mint.community.pojo.Comment;
import com.mint.community.pojo.Notification;
import com.mint.community.pojo.Question;
import com.mint.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Transactional
    public void insComment(Comment comment, User user) {
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
            commentMapper.updCommentAdd(selComment.getId());
            Question question = questionMapper.selQueById(selComment.getParentId());
            createNotification(comment, selComment.getCommentator(), user, selComment.getContent(),NotificationEnum.REPLAY_COMMENT,question.getId());
        } else {
            Question question = questionMapper.selQueById(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeStatusCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insComment(comment);
            questionMapper.updCommentAdd(question.getId());
            createNotification(comment, question.getCreator(), user, question.getTitle(),NotificationEnum.REPLAY_QUESTION,question.getId());
        }
    }

    private void createNotification(Comment comment, int id,User user,String title, NotificationEnum notificationEnum, int queId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationEnum.getType());
        notification.setOuterid(queId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(id);
        notification.setNotifierName(user.getName());
        notification.setOuterTitle(title);
        notificationMapper.insNotification(notification);
    }

//    public List<CommentDTO> selCommentByParentId(int id) {
//        List<Comment> comments = commentMapper.selCommentByParentIdAndType(id,CommentTypeEnum.QUESTION.getType());
//        List<CommentDTO> commentDTOS = new ArrayList<>();
//        if (comments.size() == 0){
//            return commentDTOS;
//        }
//        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
//        List<User> users = new ArrayList<>();
//        for (Integer commentator : commentators) {
//            User user = userMapper.selById(commentator);
//            if (user != null) {
//                users.add(user);
//            }
//        }
//        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccountId(), user -> user));
//
//        for (Comment comment : comments) {
//            CommentDTO commentDTO = new CommentDTO();
//            User user = userMap.get(comment.getCommentator());
//            if (user != null) {
//                commentDTO.setUser(user);
//            } else {
//                continue;
//            }
//            commentDTO.setComment(comment);
//            commentDTOS.add(commentDTO);
//        }
//
//        return commentDTOS;
//    }

    public List<CommentDTO> selCommentByParentIdAndType(int id, CommentTypeEnum typeEnum) {
        List<Comment> comments = commentMapper.selCommentByParentIdAndType(id,typeEnum.getType());
        List<CommentDTO> commentDTOS = new ArrayList<>();
        if (comments.size() == 0){
            return commentDTOS;
        }
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<User> users = new ArrayList<>();
        for (Integer commentator : commentators) {
            User user = userMapper.selById(commentator);
            if (user != null) {
                users.add(user);
            }
        }
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccountId(), user -> user));

        for (Comment comment : comments) {
            CommentDTO commentDTO = new CommentDTO();
            User user = userMap.get(comment.getCommentator());
            if (user != null) {
                commentDTO.setUser(user);
            } else {
                continue;
            }
            commentDTO.setComment(comment);
            commentDTOS.add(commentDTO);
        }

        return commentDTOS;
    }
}
