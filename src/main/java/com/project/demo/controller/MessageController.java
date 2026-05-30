package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.Message;
import com.project.demo.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("message")
public class MessageController extends BaseController<Message, MessageService> {
    @Autowired
    public MessageController(MessageService service) {
        setService(service);
    }

}
