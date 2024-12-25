package com.ppaw.deliveryApp.OrderClient;

import lombok.*;

import java.util.UUID;


@Data
public class PlaceOrderClientDto {


    private UUID productId;
    private Integer productQuantity;

}
