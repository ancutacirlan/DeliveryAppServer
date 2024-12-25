package com.ppaw.deliveryApp.Order;

import com.ppaw.deliveryApp.Menu.Menu;
import com.ppaw.deliveryApp.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID> {

    List<Orders> findAllByClient(Users client);
}

