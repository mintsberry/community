package com.mint.community.advice;

import com.mint.community.dto.ResultDTO;
import com.mint.community.exception.CustomizeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.exceptions.TemplateInputException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ControllerAdvice
public class CustomizeExceptionHandler {
//    @ExceptionHandler(CustomizeException.class)
//    public ModelAndView handleException(Exception e, HttpServletRequest request){
//        String contentType = request.getContentType();
//        CustomizeException customizeException = (CustomizeException) e;
//        if ("application/json".equals(contentType)){
//
//        } else {
//            ModelAndView error = new ModelAndView("error");
//            error.addObject("msg", "你看起来像走丢了");
//            return error;
//        }
//
//    }
    @ExceptionHandler(CustomizeException.class)
    public ModelAndView handleException(Exception e, HttpServletRequest request){
        String contentType = request.getContentType();
        CustomizeException customizeException = (CustomizeException) e;
        ModelAndView error = new ModelAndView("forward:error");
        ResultDTO<Object> objectResultDTO = new ResultDTO<>();
        objectResultDTO.setStatus(customizeException);
        error.addObject("status", objectResultDTO);
        return error;
    }
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handle(Exception e){
        ModelAndView error = new ModelAndView("error");
        error.addObject("message", "你看起来像走丢了");
        return error;
    }
}
