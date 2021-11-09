package com.codassassin.salestotal.controller;

import com.codassassin.salestotal.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class WorkflowController {

    @Autowired
    private WorkflowService service;

    @PostMapping("/upload/")
    public void upload() {
        service.startProcess();
    }
}