package com.example.productservicedemo.services;

import com.example.productservicedemo.models.Category;
import com.example.productservicedemo.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {

    public Product getSingleproduct(Long id);
    public Page<Product> getAllProducts(int pageNumber, int pageSize);
    public Product updateProduct(Long id, Product product);

    public Product replaceProduct(Long id, Product product);

    public Product createProduct(Product product);
    public boolean deleteProduct(Long id);

    public List<Product> getProductsInCategory(String category);

    public List<Category> getAllCategories();
}
