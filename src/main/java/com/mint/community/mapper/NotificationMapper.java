package com.mint.community.mapper;

import com.mint.community.pojo.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Insert("insert into notification(notifier,receiver,outerid,type,gmt_create,status,notifier_name,outer_title) " +
            "values(#{notifier},#{receiver},#{outerid},#{type},#{gmtCreate},#{status},#{notifierName},#{outerTitle})")
    void insNotification(Notification notification);
    @Select("select * from notification where receiver = #{accountId}")
    List<Notification> selNotificationByReceiverId(int accountId);
    @Select("select count(*) from notification where receiver = #{accountId}")
    int selNotificationCountByReceiverId(int accountId);
    @Select("select count(*) from notification where receiver = #{accountId} and status = 0")
    int selUnreadByReceiverId(int accountId);
    @Select("select * from notification where id = #{id}")
    Notification selNotificationById(int id);
    @Select("update notification set status = 1 where id = #{id}")
    void updNotificationRead(int id);
}
