package com.ppaw.deliveryApp.UserSubscription;


import javassist.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserSubscriptionService {

    void delete(UUID id);

    UserSubscriptionDto create(CreateUserSubscriptionDto createUserSubscriptionDto);

    UserSubscriptionDto update(UUID id, UserSubscriptionDto userSubscription) throws NotFoundException;

    UserSubscriptionDto getById(UUID id) throws NotFoundException;

    List<UserSubscriptionDto> getAllByUser();
}