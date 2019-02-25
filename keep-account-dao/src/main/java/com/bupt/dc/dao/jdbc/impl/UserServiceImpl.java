//package com.bupt.dc.dao.jdbc.impl;
//
//import com.bupt.dc.dao.jdbc.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Override
//    public void create(String name, Integer age, Date date) {
//        jdbcTemplate.update("insert into USER(NAME, AGE, DATE) values(?, ?, ?)", name, age, date);
//    }
//
//    @Override
//    public void deleteByName(String name) {
//        jdbcTemplate.update("delete from USER where NAME = ?", name);
//    }
//
//    @Override
//    public Integer getAllUsers() {
//        return jdbcTemplate.queryForObject("select count(1) from USER", Integer.class);
//    }
//
//    @Override
//    public void deleteAllUsers() {
//        jdbcTemplate.update("delete from USER");
//    }
//
//}
