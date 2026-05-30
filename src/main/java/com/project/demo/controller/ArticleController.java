package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.Article;
import com.project.demo.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("article")
public class ArticleController extends BaseController<Article, ArticleService> {
    @Autowired
    public ArticleController(ArticleService service) {
        setService(service);
    }
}
