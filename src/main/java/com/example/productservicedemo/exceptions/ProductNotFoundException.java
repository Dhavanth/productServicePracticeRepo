package com.example.productservicedemo.exceptions;

import com.example.productservicedemo.models.Product;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message){
        super(message);
    }
}
