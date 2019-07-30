package com.mint.community.mapper;

import com.mint.community.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into users(name, account_id, token,gmt_create,gmt_modified)" +
            "values(#{name},#{accountId},#{token},#{gmtCreate},#{getModified})")
    void insUsers(User user);
    @Select("select * from users where token = #{token}")
    User selByToken(String token);
}
