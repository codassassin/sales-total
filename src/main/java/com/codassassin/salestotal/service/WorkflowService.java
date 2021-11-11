package com.codassassin.salestotal.service;


import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WorkflowService {

        @Autowired
        private RuntimeService runtimeService;

        @Autowired
        RepositoryService repositoryService;

        @Autowired
        TaskService taskService;

        public void deployProcessDefinition() {
                Deployment deployment = repositoryService
                        .createDeployment()
                        .addClasspathResource("sales_model.bpmn20.xml")
                        .deploy();
        }

        public void startDeliveryWorkflow() {
                System.out.println("Starting workflow...");
                runtimeService.startProcessInstanceByKey("salesModel");
        }

        public void getPickupOrders() {

        }

        public void confirmOrderPickup() {

        }

        public void getDeliveryOrders() {
                List<Task> tasks = taskService.createTaskQuery().taskDefinitionId("confirmOrderPickup").list();

        }

        public void confirmDelivery(String taskid ) {
                taskService.complete(taskid);
        }

}

// courier -> delivery -> order -> pickup -> notification -> drop -> notification