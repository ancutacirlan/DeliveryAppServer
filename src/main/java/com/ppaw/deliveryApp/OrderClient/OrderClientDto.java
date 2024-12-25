package com.ppaw.deliveryApp.OrderClient;

import com.ppaw.deliveryApp.Ingredient.Ingredient;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Data
public class OrderClientDto {


    private UUID id;
    private String productName;
    private Float productPrice;
    private List<Ingredient> ingredients;
    private Integer productQuantity;

}
