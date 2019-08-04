package com.mint.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private long id;
    private long gmtCreate;
    private long status;
    private String notifierName;
    private String outerTitle;
    private String type;
}
