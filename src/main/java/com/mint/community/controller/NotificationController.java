package com.mint.community.controller;

import com.mint.community.pojo.Notification;
import com.mint.community.pojo.User;
import com.mint.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String viewNotification(@PathVariable int id, HttpSession session){
        User users = (User) (session.getAttribute("users"));
        if (users == null){
            return "/";
        }
        Notification notification = notificationService.viewNotification(users, id);
        return "redirect:/question/" + notification.getOuterid();
    }
}
