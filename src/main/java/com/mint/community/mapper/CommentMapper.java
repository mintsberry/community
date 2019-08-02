package com.mint.community.mapper;

import com.mint.community.dto.CommentDTO;
import com.mint.community.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment(parent_id,type,content,commentator,gmt_create,gmt_modified,like_count)" +
            "values(#{parentId},#{type},#{content},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount})")
    void insComment(Comment comment);
    @Select("select * from comment where id = #{parentId}")
    Comment selCommentById(int parentId);
}
