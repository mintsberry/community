package com.mint.community.config;

import com.mint.community.dto.ResultDTO;
import com.mint.community.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyConfig {
    @Autowired
    LoginInterceptor loginInterceptor;
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(loginInterceptor)
                        .addPathPatterns("/**");
            }
        };
    }
    @Bean
    public DefaultErrorAttributes errorAttributes(){
        return new DefaultErrorAttributes(){
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
                Object status = webRequest.getAttribute("status", 0);
                Map<String, Object> map = new HashMap<>();
                if (status != null && status instanceof ResultDTO){
                    ResultDTO<Object> objectResultDTO = (ResultDTO<Object>) status;
                    map.put("code", objectResultDTO.getCode());
                    map.put("message", objectResultDTO.getMessage());
                }
                return map;
            }
        };
    }

}
