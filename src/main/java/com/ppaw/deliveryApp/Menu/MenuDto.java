package com.ppaw.deliveryApp.Menu;

import com.ppaw.deliveryApp.Ingredient.Ingredient;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {


    private UUID id;
    private String name;
    private String photo;
    private Float price;
    private Boolean isAvailable = true;
    private List<Ingredient> ingredients = new ArrayList<>();
}
