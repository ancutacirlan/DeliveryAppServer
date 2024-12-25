package com.ppaw.deliveryApp.UserSubscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserSubscriptionDto implements Serializable {

    private UUID subscriptionId;
    private LocalDate startDate;
    private LocalDate endDate;

}
