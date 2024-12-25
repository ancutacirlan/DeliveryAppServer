package com.ppaw.deliveryApp.Order;

import com.ppaw.deliveryApp.OrderClient.PlaceOrderClientDto;
import lombok.Data;

import java.util.List;

@Data
public class ListOfProductsDto {

    private List<PlaceOrderClientDto> orderItems;
}

