package com.example.productservicedemo.repositories;

import com.example.productservicedemo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    Product save(Product product);

    List<Product> findByCategory_Name(String name);
}
