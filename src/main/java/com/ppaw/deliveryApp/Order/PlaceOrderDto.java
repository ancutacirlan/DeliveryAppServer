package com.ppaw.deliveryApp.Order;

import com.ppaw.deliveryApp.OrderClient.PlaceOrderClientDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlaceOrderDto {

    private String mentions;

    private List<PlaceOrderClientDto> orderItems = new ArrayList<>();
}

