package com.ppaw.deliveryApp.Subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionRestController {

    private final SubscriptionService service;
    private final ScopeSubscriptionService scopeSubscriptionService;
    private final PrototypeSubscriptionService prototypeSubscriptionService;


    @Autowired
    public SubscriptionRestController(SubscriptionService service,
                                      ScopeSubscriptionService scopeSubscriptionService,
                                      PrototypeSubscriptionService prototypeSubscriptionService) {
        this.service = service;
        this.scopeSubscriptionService = scopeSubscriptionService;
        this.prototypeSubscriptionService = prototypeSubscriptionService;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Subscription>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/test")
    public String testScopes() {
        System.out.println("Testing Singleton Service:");
        service.getAll();

        System.out.println("Testing Request Scoped Service:");
        scopeSubscriptionService.getAll();

        System.out.println("Testing Prototype Service:");
        prototypeSubscriptionService.getAll();

        return "Check the console output for scope results!";
    }

}