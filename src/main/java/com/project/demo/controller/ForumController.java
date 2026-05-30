package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.Forum;
import com.project.demo.service.ForumService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("forum")
public class ForumController extends BaseController<Forum, ForumService> {
    @Autowired
    public ForumController(ForumService service) {
        setService(service);
    }

}
