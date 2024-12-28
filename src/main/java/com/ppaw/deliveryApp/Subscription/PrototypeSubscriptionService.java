package com.ppaw.deliveryApp.Subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Scope("prototype")
@Service
public class PrototypeSubscriptionService {
    Logger logger = LoggerFactory.getLogger(PrototypeSubscriptionService.class);
    private final SubscriptionRepository repo;

    @Autowired
    public PrototypeSubscriptionService(SubscriptionRepository repo) {
        logger.info("PrototypeSubscriptionService instantiated");

        this.repo = repo;
    }


    public List<Subscription> getAll() {
        return repo.findAll();
    }
}