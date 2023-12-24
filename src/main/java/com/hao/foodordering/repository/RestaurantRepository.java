package com.hao.foodordering.repository;


import com.hao.foodordering.entity.RestaurantEntity;
import org.springframework.data.repository.ListCrudRepository;


public interface RestaurantRepository extends ListCrudRepository<RestaurantEntity, Long> {
}

