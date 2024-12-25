package com.ppaw.deliveryApp.Menu;

import com.ppaw.deliveryApp.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID> {

    List<Menu> findAllByIsAvailableIsTrue();
}

