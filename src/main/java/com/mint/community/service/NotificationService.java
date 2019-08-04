package com.mint.community.service;

import com.mint.community.dto.NotificationDTO;
import com.mint.community.dto.PaginationDTO;
import com.mint.community.enums.NotificationEnum;
import com.mint.community.exception.CustomizeException;
import com.mint.community.exception.CustomizeStatusCode;
import com.mint.community.mapper.NotificationMapper;
import com.mint.community.pojo.Notification;
import com.mint.community.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;
    public PaginationDTO selNotificationPagination(int accountId, int page, int size) {
        List<Notification> notifications = notificationMapper.selNotificationByReceiverId(accountId);
        PaginationDTO paginationDTO = new PaginationDTO();
        int count = notificationMapper.selNotificationCountByReceiverId(accountId);
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setType(NotificationEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setPagination(count,page,size,notificationDTOS);
        return paginationDTO;
    }

    public int selUnread(int accountId) {
        int i = notificationMapper.selUnreadByReceiverId(accountId);
        return i;
    }

    public Notification viewNotification(User users, int id) {
        Notification notification = notificationMapper.selNotificationById(id);
        if (notification.getReceiver() != users.getAccountId()){
            throw new CustomizeException(CustomizeStatusCode.NOTIFICATION_NOT_FOUND);
        }
        notificationMapper.updNotificationRead(id);
        return notification;
    }
}
