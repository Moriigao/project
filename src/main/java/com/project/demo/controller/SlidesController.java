package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.Slides;
import com.project.demo.service.SlidesService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("slides")
public class SlidesController extends BaseController<Slides, SlidesService> {
    @Autowired
    public SlidesController(SlidesService service) {
        setService(service);
    }

}
