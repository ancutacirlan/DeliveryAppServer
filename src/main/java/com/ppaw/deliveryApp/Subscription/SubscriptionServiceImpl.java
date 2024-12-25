package com.ppaw.deliveryApp.Subscription;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository repo;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository repo) {
        this.repo = repo;
    }


    @Override
    public List<Subscription> getAll() {
        return repo.findAll();
    }
}