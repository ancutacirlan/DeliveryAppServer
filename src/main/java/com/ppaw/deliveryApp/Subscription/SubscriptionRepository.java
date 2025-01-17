package com.ppaw.deliveryApp.Subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    Subscription getSubscriptionById(UUID subscriptionId);
    Subscription getSubscriptionByName(SubscriptionName name);
}

