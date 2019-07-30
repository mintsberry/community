package com.mint.community.dto;

import com.mint.community.pojo.User;
import lombok.Data;

@Data
public class    QuestionDTO {
    private int id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private int creator;
    private int comment_count;
    private int view_count;
    private int like_count;
    private String tag;
    private User user;
}
