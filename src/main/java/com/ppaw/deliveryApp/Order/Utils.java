package com.ppaw.deliveryApp.Order;

import com.ppaw.deliveryApp.Menu.Menu;
import com.ppaw.deliveryApp.Menu.MenuDto;
import com.ppaw.deliveryApp.OrderClient.OrderClient;
import com.ppaw.deliveryApp.OrderClient.OrderClientDto;
import org.modelmapper.PropertyMap;

public class Utils {

    static PropertyMap<OrderDto, Orders> orderMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
        }
    };

    static PropertyMap<Orders, OrderDto> orderFieldMapping = new PropertyMap<>() {
        @Override
        protected void configure() {

        }
    };

    static PropertyMap<OrderClientDto, OrderClient> orderClientMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
        }
    };

    static PropertyMap<OrderClient, OrderClientDto> orderClientFieldMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().setProductName(source.getProduct().getName());
            map().setProductPrice(source.getProduct().getPrice());
            map().setIngredients(source.getProduct().getIngredients());
            map().setProductQuantity(source.getProductQuantity());

        }
    };

    private Utils() {

    }

}
