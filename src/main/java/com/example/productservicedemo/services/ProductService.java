package com.example.productservicedemo.services;

import com.example.productservicedemo.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {

    public Product getSingleproduct(Long id);
    public List<Product> getAllProducts();
    public Product updateProduct(Long id, Product product);

    public Product replaceProduct(Long id, Product product);

    public Product createProduct(Product product);
    public boolean deleteProduct(Long id);

    public List<Product> getProductsInCategory(String category);
}
