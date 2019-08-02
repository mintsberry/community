package com.mint.community.enums;

import com.mint.community.pojo.Comment;

public enum  CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private int type;
    CommentTypeEnum(int id){
        this.type = id;
    }

    public static boolean isExist(long type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (type == value.type){
                return true;
            }
        }
        return  false;
    }
    public int getType(){
        return type;
    }
}
