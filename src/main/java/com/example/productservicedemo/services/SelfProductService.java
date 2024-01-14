package com.example.productservicedemo.services;

import com.example.productservicedemo.exceptions.ProductNotFoundException;
import com.example.productservicedemo.models.Product;
import com.example.productservicedemo.repositories.CategoryRepository;
import com.example.productservicedemo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
// Why did we name this service?
// In Product Controller, we have a ProductService attribute
// While autowiring, Spring gets 2 beans since fake and self both are implementing the interface
// To fetch the desired bean, we are naming the service
public class SelfProductService implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Product getSingleproduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product  with id: "+ id + " is not found!");
        }
        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean deleteProduct(Long id) {
        return false;
    }

    @Override
    public List<Product> getProductsInCategory(String category) {
        return null;
    }
}
