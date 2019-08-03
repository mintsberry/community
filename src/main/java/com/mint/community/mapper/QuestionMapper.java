package com.mint.community.mapper;

import com.mint.community.pojo.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.annotation.Id;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title, description, gmt_create, gmt_modified, creator, tag)" +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void insQue(Question question);
    @Select("select * from question ORDER BY gmt_create desc")
    List<Question> selListQue();
    @Select("select * from question ORDER BY gmt_create desc limit #{offset},#{size} ")
    List<Question> selPageQue(int offset, int size);
    @Select("select count(*) from question")
    int selPageCount();
    @Select("select * from question where creator = #{accountId} ORDER BY gmt_create desc limit #{offset},#{size} ")
    List<Question> selPageQueByUserId(int accountId, int offset, int size);
    @Select("select count(*) from question where creator = #{accountId} ")
    int selPageCountByUserId(int accountId);
    @Select("select * from question where id = #{id}")
    Question selQueById(int id);
    @Update("update question set title = #{title}, description = #{description}, tag = #{tag}, gmt_modified = #{gmtModified}  where id = #{id}")
    void updArticleById(Question question);
    @Update("update question set view_Count = view_Count+ 1 where id = #{id}")
    void updViewAdd(int id);
    @Update("update question set comment_Count = comment_Count+ 1 where id = #{id}")
    void updCommentAdd(int id);
    @Select("select * from question where tag regexp #{regex} and id != #{id}")
    List<Question> selRelevantQue(int id, String regex);
}
