package com.ppaw.deliveryApp.Order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderPricesDto {

    private Double totalPrice;
    private Double transportPrice;
    private Double productsPrice;


}

