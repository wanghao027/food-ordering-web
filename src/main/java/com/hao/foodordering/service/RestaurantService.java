package com.hao.foodordering.service;

import com.hao.foodordering.entity.MenuItemEntity;
import com.hao.foodordering.entity.RestaurantEntity;
import com.hao.foodordering.model.MenuItemDto;
import com.hao.foodordering.model.RestaurantDto;
import com.hao.foodordering.repository.MenuItemRepository;
import com.hao.foodordering.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RestaurantService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository,
                             MenuItemRepository menuItemRepository){
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Cacheable("restaurants")
    public List<RestaurantDto> getRestaurants() {
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAll();
        List<MenuItemEntity> menuItemEntities = menuItemRepository.findAll();
        Map<Long, List<MenuItemDto>> groupedMenuItems = new HashMap<>();
        for(MenuItemEntity menuItemEntity : menuItemEntities){
            List<MenuItemDto> group = groupedMenuItems.computeIfAbsent(menuItemEntity.restaurantId(), k -> new ArrayList<>());
            MenuItemDto menuItemDto = new MenuItemDto(menuItemEntity);
            group.add(menuItemDto);
        }

        List<RestaurantDto> results = new ArrayList<>();
        for(RestaurantEntity restaurantEntity : restaurantEntities){
            RestaurantDto restaurantDto = new RestaurantDto(restaurantEntity, groupedMenuItems.get(restaurantEntity.id()));
            results.add(restaurantDto);
        }
        return results;
    }
}
