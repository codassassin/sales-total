package com.codassassin.salestotal.service;


import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WorkflowService {

        @Autowired
        private RuntimeService runtimeService;

        public void startProcess() {
                System.out.println("Starting workflow...");
                runtimeService.startProcessInstanceByKey("salesModel");
        }
}
