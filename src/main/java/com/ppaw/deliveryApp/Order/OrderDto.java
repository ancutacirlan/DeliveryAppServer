package com.ppaw.deliveryApp.Order;

import com.ppaw.deliveryApp.OrderClient.OrderClient;
import com.ppaw.deliveryApp.OrderClient.OrderClientDto;
import com.ppaw.deliveryApp.OrderClient.PlaceOrderClientDto;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {


    private UUID id;
    private Float totalPrice;
    private Status status;
    private String mentions;
    private List<OrderClientDto> orderItems;
}

