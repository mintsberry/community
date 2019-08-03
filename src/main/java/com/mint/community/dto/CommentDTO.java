package com.mint.community.dto;

import com.mint.community.pojo.Comment;
import com.mint.community.pojo.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Comment comment;
    private User user;
}
