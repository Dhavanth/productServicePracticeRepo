package com.example.productservicedemo.repositories;

import com.example.productservicedemo.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);
    // SELECT * FROM product where id = ?

    Page<Product> findAll(Pageable pageable);
    Product save(Product product);

    List<Product> findByCategory_Name(String name);

    Page<Product> findByCategory_Id(Long id, Pageable pageable);
}
