package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.Praise;
import com.project.demo.service.PraiseService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("praise")
public class PraiseController extends BaseController<Praise, PraiseService> {
    @Autowired
    public PraiseController(PraiseService service) {
        setService(service);
    }

}
