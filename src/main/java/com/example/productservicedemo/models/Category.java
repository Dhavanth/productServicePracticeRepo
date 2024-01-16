package com.example.productservicedemo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {

    private String name;
    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.REMOVE
            //fetch = FetchType.EAGER
            // Default fetch type for collection is LAZY
    )
    private List<Product> products;
}
