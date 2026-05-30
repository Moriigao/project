package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.ForumType;
import com.project.demo.service.ForumTypeService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("forum_type")
public class ForumTypeController extends BaseController<ForumType, ForumTypeService> {
    @Autowired
    public ForumTypeController(ForumTypeService service) {
        setService(service);
    }

}
