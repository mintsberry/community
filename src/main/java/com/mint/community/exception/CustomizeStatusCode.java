package com.mint.community.exception;

public enum CustomizeStatusCode implements ICustomizeErrorCode{
    SUCCESS(2000,"请求成功"),
    TARGET_PARAM_NOT_FOUND( 2001,"评论走丢了,重试一下吧"),
    ACCOUNT_NOT_LOGIN(2002,"未登录"),
    QUESTION_NOT_FOUND( 2003,"问题弄丢啦"),
    TYPE_PARAM_WRONG(2004,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2005,"评论不存在"),
    COMMENT_IS_EMPTY(2006,"输入内容为空"),
    NOTIFICATION_NOT_FOUND(2007,"找错通知了");


    private String message;
    private int code;
    CustomizeStatusCode(int code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }
}
