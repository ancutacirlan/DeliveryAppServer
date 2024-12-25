package com.ppaw.deliveryApp.User;

import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private UUID id;
    private String name;

}
