package com.hao.foodordering.hello;


public record Person(
        String name,
        String company,
        Address homeAddress,
        Book favoriteBook
) {
}
