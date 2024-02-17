package com.example.productservicedemo.controllers;

import com.example.productservicedemo.commons.AuthenticationCommons;
import com.example.productservicedemo.dtos.Role;
import com.example.productservicedemo.dtos.UserDto;
import com.example.productservicedemo.exceptions.ProductNotFoundException;
import com.example.productservicedemo.models.Category;
import com.example.productservicedemo.models.Product;
import com.example.productservicedemo.repositories.CategoryRepository;
import com.example.productservicedemo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private CategoryRepository categoryRepository;

    private AuthenticationCommons authenticationCommons;

    @Autowired
    public ProductController(@Qualifier("selfProductService") ProductService productService,
                             CategoryRepository categoryRepository,
                             AuthenticationCommons authenticationCommons){
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.authenticationCommons = authenticationCommons;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(
            //@RequestHeader("AuthenticationToken") String token
    ){

//        // 1. Once I receive the token, I have to validate the token
//        UserDto userDto = authenticationCommons.validateToken(token);
//
//        if(userDto == null){
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//        boolean isAdmin = false;
//        for(Role role: userDto.getRoles()){
//            if(role.getRoleName().equals("ADMIN")){
//                isAdmin = true;
//                break;
//            }
//        }
//
//        if(!isAdmin){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }

        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") Long id){

        try{
            return new ResponseEntity<>(productService.getSingleproduct(id), HttpStatus.OK);
        }catch(ProductNotFoundException exception){
            ResponseEntity<Product> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return response;
        }
    }

    @PostMapping()
    public Product addNewProduct(@RequestBody Product product){

        Optional<Category> optionalCategory = categoryRepository.findByName(product.getCategory().getName());
        if(optionalCategory.isEmpty()){
            Category savedCategory = categoryRepository.save(product.getCategory());
            product.setCategory(savedCategory);
        }else{
            product.setCategory(optionalCategory.get());
        }

        return productService.createProduct(product);
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product){

        return productService.updateProduct(id, product);
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return productService.replaceProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
    }

    @GetMapping("/categories/{category}")
    public List<Product> getProductsInCategory(@PathVariable("category") String category){
        return productService.getProductsInCategory(category);
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return productService.getAllCategories();
    }
}
