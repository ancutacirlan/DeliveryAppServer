package com.ppaw.deliveryApp.User;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

   // Users saveUser(Users user);
    List<Users> getAllUsers();
    Users getUserById(UUID id);
    void deleteUserById(UUID id);
    void updateUser(Users user);
    List<Users> getAllRestaurants();

    Users findByEmail(@NotEmpty(message = "Email should not be empty") @Email String email);

    ResponseEntity saveUser(UserDto userDto);

    List<UserDto> findAllUsers();
}