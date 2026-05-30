package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.Comment;
import com.project.demo.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
public class CommentController extends BaseController<Comment, CommentService> {
    @Autowired
    public CommentController(CommentService service) {
        setService(service);
    }

}
