package com.hao.foodordering.repository;


import com.hao.foodordering.entity.CartEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;


public interface CartRepository extends CrudRepository<CartEntity, Long> {


    CartEntity getByCustomerId(Long customerId);


    @Modifying
    @Query("UPDATE carts SET total_price = :totalPrice WHERE id = :cartId")
    void updateTotalPrice(Long cartId, Double totalPrice);
}

