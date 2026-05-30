package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.Auth;
import com.project.demo.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController extends BaseController<Auth, AuthService> {
    @Autowired
    public AuthController(AuthService service) {
        setService(service);
    }

}
