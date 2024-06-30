package com.example.productservicedemo.dtos;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FakeStoreProductsDto {
    private Long id;
    private String title;
    private double price; // Fakestore api is sending the price as double, so we used datatype as double
    private String category; // Fakestore api is sending the category as String, so we used datatype as string
    private String description;
    private String image;


}
