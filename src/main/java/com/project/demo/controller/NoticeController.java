package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.Notice;
import com.project.demo.service.NoticeService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notice")
public class NoticeController extends BaseController<Notice, NoticeService> {
    @Autowired
    public NoticeController(NoticeService service) {
        setService(service);
    }

}
