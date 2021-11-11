package com.codassassin.salestotal.controller;

import com.codassassin.salestotal.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class WorkflowController {

    @Autowired
    private WorkflowService service;

    @GetMapping("/deploy")
    public void deploy(){
        System.out.println("Deploying the process definition");
        service.deployProcessDefinition();
    }

    @PostMapping("/delivery/request/new")
    public void newDeliveryRequest() {
        System.out.println("Received new delivery request form user");
        service.startDeliveryWorkflow();
    }

    @GetMapping("/pickup/orders")
    public void getAllPickupOrders() {
        System.out.println(" Get the list of all pickup orders for delivery executive ");

    }

    @PostMapping("/order/pickup/confirm")
    public void confirmOrderPickup() {
        System.out.println("Order picked up by delivery partner");
    }

    @GetMapping("/delivery/orders")
    public void getListOfPendingDeliveries() {

    }

    @PostMapping("/order/delivery/confirm")
    public void confirmOrderDelivery(@RequestParam String taskid) {
        System.out.println("Order delivered by delivvery partner ");
        service.confirmDelivery(taskid);
    }
}