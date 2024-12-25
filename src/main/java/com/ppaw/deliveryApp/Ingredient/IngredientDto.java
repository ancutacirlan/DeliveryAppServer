package com.ppaw.deliveryApp.Ingredient;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

public class IngredientDto {

    private UUID id;
    private String name;
}
