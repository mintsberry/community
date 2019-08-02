package com.mint.community.dto;

import com.mint.community.pojo.User;
import lombok.Data;
@Data
public class QuestionDTO {
    private int id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private int creator;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private String tag;
    private User user;
}
