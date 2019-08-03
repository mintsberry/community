package com.mint.community.service;

import com.mint.community.dto.PaginationDTO;
import com.mint.community.dto.QuestionDTO;
import com.mint.community.mapper.QuestionMapper;
import com.mint.community.mapper.UserMapper;
import com.mint.community.pojo.Question;
import com.mint.community.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
//    public List<QuestionDTO> insAllQue() {
//        List<QuestionDTO> questionDTOS = new ArrayList<>();
//        List<Question> questions = questionMapper.selListQue();
//        for (Question question : questions) {
//            User user = userMapper.selById(question.getCreator());
//            QuestionDTO questionDTO = new QuestionDTO();
//            BeanUtils.copyProperties(question, questionDTO);
//            questionDTO.setUser(user);
//            questionDTOS.add(questionDTO);
//        }
//        return questionDTOS;
//    }

    public PaginationDTO selListQue(int page, int size) {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        int offset = (page - 1) * size;
        List<Question> questions = questionMapper.selPageQue(offset, size);
        for (Question question : questions) {
            User user = userMapper.selById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        int count = questionMapper.selPageCount();
        paginationDTO.setPagination(count, page, size, questionDTOS);
        return paginationDTO;
    }

    public PaginationDTO selListQueByUserId(int accountId, int page, int size) {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        int offset = (page - 1) * size;
        List<Question> questions = questionMapper.selPageQueByUserId(accountId,offset, size);
        for (Question question : questions) {
            User user = userMapper.selById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        int count = questionMapper.selPageCountByUserId(accountId);
        paginationDTO.setPagination(count, page, size, questionDTOS);
        return paginationDTO;
    }

    public QuestionDTO selQueById(int id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selQueById(id);
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(userMapper.selById(questionDTO.getCreator()));
        return questionDTO;

    }

    public void incReaderCount(int id) {
        questionMapper.updViewAdd(id);
    }

    public List<Question> selRelevantTopics(QuestionDTO question) {
        int id = question.getId();
        String tag = question.getTag();
        String regex = tag.replaceAll(",|，", "|");
        List<Question> questions = questionMapper.selRelevantQue(id, regex);
        return questions;
    }
}
