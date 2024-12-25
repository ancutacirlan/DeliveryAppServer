package com.ppaw.deliveryApp.User;

import com.ppaw.deliveryApp.Utils.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    Users findByName(String name);
    Users findByEmail(String email);
    List<Users>findAllByRole(Role role);

    boolean existsByName(String name);
    boolean existsByEmail(String email);

}

