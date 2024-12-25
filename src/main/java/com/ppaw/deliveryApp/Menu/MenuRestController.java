package com.ppaw.deliveryApp.Menu;

import com.ppaw.deliveryApp.Ingredient.IngredientService;
import com.ppaw.deliveryApp.User.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/menu")
public class MenuRestController {

    private final MenuService service;


    @Autowired
    public MenuRestController(MenuService service, ModelMapper modelMapper) {
        this.service = service;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MenuDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping(path = "/grouping/by/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGroupByRestaurant() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName())    ;
        return ResponseEntity.ok(service.getMenusGroupedByRestaurant());
    }


    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuDto> update(@RequestBody @Valid MenuDto dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuDto> create(@RequestBody @Valid MenuDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

}