package com.mint.community.mapper;

import com.mint.community.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment(parent_id,type,content,commentator,gmt_create,gmt_modified,like_count)" +
            "values(#{parentId},#{type},#{content},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount})")
    void insComment(Comment comment);
    @Select("select * from comment where id = #{parentId}")
    Comment selCommentById(int Id);
    @Select("select * from comment where parent_id = #{parentId} and type = #{type}")
    List<Comment> selCommentByParentIdAndType(int parentId, int type);
    @Update("update comment set comment_Count = comment_Count+ 1 where id = #{id}")
    void updCommentAdd(int id);
}
