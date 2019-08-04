package com.mint.community.pojo;


public class Notification {

  private long id;
  private long notifier;
  private long receiver;
  private long outerid;
  private int type;
  private long gmtCreate;
  private long status;
  private String notifierName;
  private String outerTitle;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getNotifier() {
    return notifier;
  }

  public void setNotifier(long notifier) {
    this.notifier = notifier;
  }


  public long getReceiver() {
    return receiver;
  }

  public void setReceiver(long receiver) {
    this.receiver = receiver;
  }


  public long getOuterid() {
    return outerid;
  }

  public void setOuterid(long outerid) {
    this.outerid = outerid;
  }


  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }


  public long getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(long gmtCreate) {
    this.gmtCreate = gmtCreate;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }


  public String getNotifierName() {
    return notifierName;
  }

  public void setNotifierName(String notifierName) {
    this.notifierName = notifierName;
  }


  public String getOuterTitle() {
    return outerTitle;
  }

  public void setOuterTitle(String outerTitle) {
    this.outerTitle = outerTitle;
  }

}
