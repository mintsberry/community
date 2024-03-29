package com.mint.community.enums;

public enum  NotificationEnum {
    REPLAY_QUESTION(0,"回复了问题"),
    REPLAY_COMMENT(1,"回复了评论");
    private int type;
    private String name;
    NotificationEnum(int type, String name){
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static String nameOfType(int type){
        for (NotificationEnum value : NotificationEnum.values()) {
            if (value.getType() == type) {
                return value.getName();
            }
        }
        return "";
    }
}
