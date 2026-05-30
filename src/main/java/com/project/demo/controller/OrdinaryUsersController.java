package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.OrdinaryUsers;
import com.project.demo.service.OrdinaryUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordinary_users")
public class OrdinaryUsersController extends BaseController<OrdinaryUsers, OrdinaryUsersService> {
    @Autowired
    public OrdinaryUsersController(OrdinaryUsersService service) {
        setService(service);
    }
}
