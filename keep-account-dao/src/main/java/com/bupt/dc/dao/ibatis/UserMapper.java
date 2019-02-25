//package com.bupt.dc.dao.ibatis.service;
//
//import User;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;
//
//import java.util.Date;
//
//@Mapper
//public interface UserMapper {
//
//    @Select("SELECT * FROM USER WHERE NAME = #{name}")
//    User findByName(@Param("name") String name);
//
//    @Insert("INSERT INTO USER(ID, NAME, AGE, DATE) VALUES(#{id}, #{name}, #{age}, #{date})")
//    int insert(@Param("id") Integer id, @Param("name") String name, @Param("age") Integer age, @Param("date") Date date);
//
//}
