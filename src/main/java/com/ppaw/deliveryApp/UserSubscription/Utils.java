package com.ppaw.deliveryApp.UserSubscription;

import org.modelmapper.PropertyMap;

public class Utils {

    static PropertyMap<UserSubscription, UserSubscriptionDto> userSubscriptionFieldMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().setSubscription(source.getSubscription().getName());
            map().setSubscriptionId(source.getSubscription().getId());
        }
    };



    private Utils() {

    }

}
