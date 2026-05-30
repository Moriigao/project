package com.project.demo.service;

import com.project.demo.dao.UserMapper;
import com.project.demo.entity.User;
import com.project.demo.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User> {
    @Autowired
    UserMapper userMapper;
    public String selectExamineState(String sourceTable, Integer userId) {
        return userMapper.selectExamineState(sourceTable,userId);
    }
}


