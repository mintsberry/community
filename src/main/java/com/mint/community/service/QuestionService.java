package com.mint.community.service;

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
    public List<QuestionDTO> insListQue() {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        List<Question> questions = questionMapper.selListQue();
        for (Question question : questions) {
            User user = userMapper.selById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }
}
