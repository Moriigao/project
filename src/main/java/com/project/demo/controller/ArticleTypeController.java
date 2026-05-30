package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.ArticleType;
import com.project.demo.service.ArticleTypeService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("article_type")
public class ArticleTypeController extends BaseController<ArticleType, ArticleTypeService> {
    @Autowired
    public ArticleTypeController(ArticleTypeService service) {
        setService(service);
    }

}
