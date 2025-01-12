package com.ppaw.deliveryApp.Order;

import com.ppaw.deliveryApp.Exceptions.NotFoundException;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface OrderService {

    OrderDto create(@NotNull PlaceOrderDto dto);
    List<OrderDto> getAll();
    OrderDto getById(UUID id) throws NotFoundException;
    void delete(UUID id) throws Exception;
    OrderDto update(UUID id, OrderStatusDto orderStatusDto) throws NotFoundException;
    OrderPricesDto calculateTotalPrice(ListOfProductsDto dto);
}

