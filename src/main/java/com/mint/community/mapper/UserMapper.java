package com.mint.community.mapper;

import com.mint.community.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Insert("insert into users(name, account_id, token,gmt_create,gmt_modified, avatar_url)" +
            "values(#{name},#{accountId},#{token},#{gmtCreate},#{getModified},#{avatarUrl})")
    void insUsers(User user);
    @Select("select * from users where token = #{token}")
    User selByToken(String token);
    @Select("select * from users where account_id = #{creator}")
    User selById(int creator);
    @Update("update users set token = #{token} where  account_id = #{accountId}")
    void updTokenById(User user);
}
