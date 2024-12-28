package com.ppaw.deliveryApp.Subscription;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RequestScope
@Service
public class ScopeSubscriptionService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SubscriptionRepository repo;

    @Autowired
    public ScopeSubscriptionService(SubscriptionRepository repo) {

        logger.info("ScopeSubscriptionService instantiated");
        this.repo = repo;
    }

    public List<Subscription> getAll() {
        return repo.findAll();
    }
}