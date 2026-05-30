package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.Hits;
import com.project.demo.service.HitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hits")
public class HitsController extends BaseController<Hits, HitsService> {
    @Autowired
    public HitsController(HitsService service) {
        setService(service);
    }

}
