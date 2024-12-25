package com.ppaw.deliveryApp.Menu;


import com.ppaw.deliveryApp.User.RestaurantDto;
import com.ppaw.deliveryApp.User.Users;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface MenuService {

    Menu saveMenu(Menu menu);

    MenuDto getById(@NotNull UUID id);

    List<Menu> getAllMenu();


    MenuDto create(@NotNull MenuDto dto);

    List<MenuDto> getAll();


    MenuDto update(MenuDto dto);


    List<MenuRestaurantDto> getMenusGroupedByRestaurant();
    //Map<RestaurantDto, List<MenuDto>> getMenusGroupedByRestaurant();
}