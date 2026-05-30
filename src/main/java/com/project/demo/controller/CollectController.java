package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.Collect;
import com.project.demo.service.CollectService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("collect")
public class CollectController extends BaseController<Collect, CollectService> {
    @Autowired
    public CollectController(CollectService service) {
        setService(service);
    }

}
