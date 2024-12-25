package com.ppaw.deliveryApp.UserSubscription;

import com.ppaw.deliveryApp.Subscription.Subscription;
import com.ppaw.deliveryApp.Subscription.SubscriptionName;
import com.ppaw.deliveryApp.User.Users;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSubscriptionDto implements Serializable {


    private UUID id;
    private UUID subscriptionId;
    private SubscriptionName subscription;
    private LocalDate startDate;
    private LocalDate endDate;
}
