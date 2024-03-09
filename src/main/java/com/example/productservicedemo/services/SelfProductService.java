package com.example.productservicedemo.services;

import com.example.productservicedemo.exceptions.CategoryNotFoundException;
import com.example.productservicedemo.exceptions.ProductNotFoundException;
import com.example.productservicedemo.models.Category;
import com.example.productservicedemo.models.Product;
import com.example.productservicedemo.repositories.CategoryRepository;
import com.example.productservicedemo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
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
    public Page<Product> getAllProducts(int pageNumber,
                                        int pageSize,
                                        String sortBy,
                                        String sortOrder) {

        Sort sort = Sort.by(sortBy).descending().and(Sort.by("title").ascending());

        return productRepository.findAll(
                PageRequest.of(
                        pageNumber,
                        pageSize,
                        sort
                )
        );
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product  with id: "+ id + " is not found!");
        }
        Product existingProduct = optionalProduct.get();

        if(product.getTitle() != null){
            existingProduct.setTitle(product.getTitle());
        }
        if(product.getDescription() != null){
            existingProduct.setDescription(product.getDescription());
        }
        if(product.getPrice() != null){
            existingProduct.setPrice(product.getPrice());
        }
        if(product.getImage() != null){
            existingProduct.setImage(product.getImage());
        }
        if(product.getCategory() != null){
            existingProduct.setCategory(product.getCategory());
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product  with id: "+ id + " is not found!");
        }
        Product existingProduct = optionalProduct.get();
        existingProduct.setTitle(product.getTitle());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImage(product.getImage());
        Optional<Category> optionalCategory = categoryRepository.findByName(product.getCategory().getName());
        if(optionalCategory.isEmpty()){
            Category savedCategory = categoryRepository.save(product.getCategory());
            existingProduct.setCategory(savedCategory);
        }else{
            existingProduct.setCategory(optionalCategory.get());
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product  with id: "+ id + " is not found!");
        }else {
            productRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public List<Product> getProductsInCategory(String category) {
        Optional<Category> optionalCategory = categoryRepository.findByName(category);
        if(optionalCategory.isEmpty()){
            throw new CategoryNotFoundException("Category  with name: "+ category + " is not found!");
        }else{
            return productRepository.findByCategory_Name(category);
        }

    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


}
