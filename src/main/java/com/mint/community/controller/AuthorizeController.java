package com.mint.community.controller;

import com.mint.community.dto.AccesstokenDTO;
import com.mint.community.dto.GithubUser;
import com.mint.community.mapper.UserMapper;
import com.mint.community.pojo.User;
import com.mint.community.provider.GithubProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id(clientId);
        accesstokenDTO.setClient_secret(clientSecret);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(redirectUrl);
        accesstokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accesstokenDTO);
        GithubUser githubUser = githubProvider.getUsers(accessToken);
        Logger logger = LoggerFactory.getLogger(AuthorizeController.class);
        if (githubUser != null && githubUser.getLogin() != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getLogin());
            user.setAccountId(githubUser.getId());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGetModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            User selUser = userMapper.selById(user.getAccountId());
            if (selUser == null) {
                userMapper.insUsers(user);
            } else {
                userMapper.updTokenById(user);
            }
            //写入Cookie
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
            logger.debug("User login" + githubUser);
            return "redirect:index";
        } else {
            return "redirect:index";
        }
    }
}
