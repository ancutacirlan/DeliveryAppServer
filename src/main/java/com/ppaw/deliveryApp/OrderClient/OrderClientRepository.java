package com.ppaw.deliveryApp.OrderClient;

import com.ppaw.deliveryApp.Order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderClientRepository extends JpaRepository<OrderClient, UUID> {

    List<OrderClient>  getOrderClientByOrder(Orders savedOrder);
}

