package com.example.productservicedemo.exceptions;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String message){
        super(message);
    }
}
