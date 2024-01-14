package com.example.productservicedemo.controllerAdvice;

import com.example.productservicedemo.dtos.ExceptionDto;
import com.example.productservicedemo.exceptions.ProductNotFoundException;
import com.example.productservicedemo.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandlers {

    public ResponseEntity<ExceptionDto> handleProductNotFoundException(
            ProductNotFoundException productNotFoundException){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(productNotFoundException.getMessage());

        return new ResponseEntity<>(exceptionDto, HttpStatus.OK);
    }
}
