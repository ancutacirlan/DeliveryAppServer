package com.ppaw.deliveryApp.Subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    Logger logger = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
    private final SubscriptionRepository repo;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository repo) {
        logger.info("SingletonSubscriptionService instantiated");

        this.repo = repo;
    }


    @Override
    public List<Subscription> getAll() {
        return repo.findAll();
    }
}