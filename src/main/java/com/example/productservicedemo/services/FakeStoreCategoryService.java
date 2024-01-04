package com.example.productservicedemo.services;

import com.example.productservicedemo.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service
public class FakeStoreCategoryService implements CategoryService{

    private RestTemplate restTemplate;

    @Autowired
    public FakeStoreCategoryService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @Override
    public List<String> getAllCategories() {
        String[] categoriesList = restTemplate.getForObject(
                "https://fakestoreapi.com/products/categories",
                String[].class
        );

        List<String> categories = new ArrayList<>();
        for(String category: categoriesList){
            categories.add(category);
        }

        return categories;
    }
}
