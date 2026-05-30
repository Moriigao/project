package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.UserGroup;
import com.project.demo.service.UserGroupService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user_group")
public class UserGroupController extends BaseController<UserGroup, UserGroupService> {
    @Autowired
    public UserGroupController(UserGroupService service) {
        setService(service);
    }
}
