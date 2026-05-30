package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.SongClassification;
import com.project.demo.service.SongClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/song_classification")
public class SongClassificationController extends BaseController<SongClassification, SongClassificationService> {
    @Autowired
    public SongClassificationController(SongClassificationService service) {
        setService(service);
    }
}
