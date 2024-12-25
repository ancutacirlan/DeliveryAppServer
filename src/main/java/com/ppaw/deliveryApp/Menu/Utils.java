package com.ppaw.deliveryApp.Menu;

import com.ppaw.deliveryApp.User.RestaurantDto;
import com.ppaw.deliveryApp.User.Users;
import org.modelmapper.PropertyMap;

public class Utils {

    static PropertyMap<MenuDto, Menu> menuMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
        }
    };

    static PropertyMap<Menu, MenuDto> menuFieldMapping = new PropertyMap<>() {
        @Override
        protected void configure() {

        }
    };


    static PropertyMap<Users, RestaurantDto> restaurantMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
        }
    };

    static PropertyMap<RestaurantDto, Users> restaurantFieldMapping = new PropertyMap<>() {
        @Override
        protected void configure() {

        }
    };

    private Utils() {

    }

}
