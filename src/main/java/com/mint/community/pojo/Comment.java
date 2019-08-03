package com.mint.community.pojo;


public class Comment {

  private int id;
  private int parentId;
  private long type;
  private String content;
  private int commentator;
  private long gmtCreate;
  private long gmtModified;
  private long likeCount;
  private long commentCount;

  public long getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(long commentCount) {
    this.commentCount = commentCount;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public int getParentId() {
    return parentId;
  }

  public void setParentId(int parentId) {
    this.parentId = parentId;
  }


  public long getType() {
    return type;
  }

  public void setType(long type) {
    this.type = type;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public int getCommentator() {
    return commentator;
  }

  public void setCommentator(int commentator) {
    this.commentator = commentator;
  }


  public long getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(long gmtCreate) {
    this.gmtCreate = gmtCreate;
  }


  public long getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(long gmtModified) {
    this.gmtModified = gmtModified;
  }


  public long getLikeCount() {
    return likeCount;
  }

  public void setLikeCount(long likeCount) {
    this.likeCount = likeCount;
  }

}
