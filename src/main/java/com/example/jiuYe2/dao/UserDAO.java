package com.example.jiuYe2.dao;

import com.example.jiuYe2.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDAO {

    String TABLE_NAME = "user";
    String INSERT_FIELDS = "name, password";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{name}, #{password})"})
    void insert(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id = #{id}"})
    User selectById(Integer id);

    void updateById(User user);

}