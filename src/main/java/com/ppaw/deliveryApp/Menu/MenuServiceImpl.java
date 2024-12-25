package com.ppaw.deliveryApp.Menu;


import com.ppaw.deliveryApp.User.RestaurantDto;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository repo;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuServiceImpl(MenuRepository repo, ModelMapper modelMapper) {
        this.repo = repo;
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(Utils.menuMapping);
        this.modelMapper.addMappings(Utils.restaurantMapping);
        this.modelMapper.addMappings(Utils.restaurantFieldMapping);
        this.modelMapper.addMappings(Utils.menuFieldMapping);

    }

    @Override
    public Menu saveMenu(Menu menu) {
        return repo.save(menu);
    }


    @Override
    public List<Menu> getAllMenu() {
        return repo.findAll();
    }

    @Override
    public MenuDto create(MenuDto dto) {
        Menu menu = modelMapper.map(dto, Menu.class);
        return modelMapper.map(repo.save(menu), MenuDto.class);
    }

    @Override
    public MenuDto getById(UUID id) {
        return repo
                .findById(id)
                .map(result -> modelMapper.map(result, MenuDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public List<MenuDto> getAll() {
        return repo
                .findAll()
                .stream()
                .map(item -> modelMapper.map(item, MenuDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public MenuDto update(MenuDto dto) {
        return repo
                .findById(dto.getId())
                .map(result -> {
                    Menu appTypeToBeUpdated = modelMapper.map(dto, Menu.class);
                    return modelMapper.map(repo.save(appTypeToBeUpdated), MenuDto.class);
                })
                .orElseThrow(EntityNotFoundException::new);

    }

    @Override
    public List<MenuRestaurantDto> getMenusGroupedByRestaurant() {
        List<Menu> menus = repo.findAllByIsAvailableIsTrue();

        // Grupăm meniurile după restaurant și le mapăm la RestaurantDto și MenuDto
        return menus.stream()
                .collect(Collectors.groupingBy(
                        menu -> modelMapper.map(menu.getRestaurant(), RestaurantDto.class)  // Mapare Restaurant -> RestaurantDto
                ))
                .entrySet().stream()
                .map(entry -> new MenuRestaurantDto(entry.getKey(),
                        entry.getValue().stream()
                                .map(menu -> modelMapper.map(menu, MenuDto.class))  // Mapare Menu -> MenuDto
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

    }

}