package com.ppaw.deliveryApp.Menu;

import com.ppaw.deliveryApp.Ingredient.Ingredient;
import com.ppaw.deliveryApp.User.RestaurantDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuRestaurantDto {


    private RestaurantDto restaurantDto;
    private List<MenuDto> menuDtos;
}
