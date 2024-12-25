package com.ppaw.deliveryApp.Ingredient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository repo;


    @Override
    public List<Ingredient> getAllIngredients() {
        return repo.findAll();
    }

}