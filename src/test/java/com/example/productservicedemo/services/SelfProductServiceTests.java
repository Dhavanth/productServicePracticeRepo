package com.example.productservicedemo.services;

import com.example.productservicedemo.models.Category;
import com.example.productservicedemo.models.Product;
import com.example.productservicedemo.repositories.CategoryRepository;
import com.example.productservicedemo.repositories.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SelfProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private SelfProductService selfProductService;

    // Write your tests here
    
    @Test
    public void testGetSingleProduct() {
        // Write your test here
        // ARRANGE
        Product product = Mockito.mock(Product.class);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // ACT
        Product singleProduct = selfProductService.getSingleproduct(1L);

        // ASSERT
        Assertions.assertThat(singleProduct).isNotNull();


    }

    @Test
    public void testGetAllProducts() {
        // ARRANGE
        Page<Product> products = Mockito.mock(Page.class); // Mocking the return type of getAllProducts() method
        when(productRepository.findAll(Mockito.any(Pageable.class))).thenReturn(products);

        // ACT
        Page<Product> allProducts = selfProductService.getAllProducts(0, 10, "title", "asc"); // random values

        // ASSERT
        Assertions.assertThat(allProducts).isNotNull();
    }

    @Test
    public void testGetAllProductsByCategory() {
        // Write your test here
        // ARRANGE
        Category category = Mockito.mock(Category.class);
        String categoryName = "Electronics";
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));
        when(productRepository.findByCategory_Name(categoryName)).thenReturn(Mockito.mock(List.class));

        // ACT
        List<Product> products = selfProductService.getProductsInCategory(categoryName);

        // ASSERT
        Assertions.assertThat(products).isNotNull();

    }

    @Test
    public void testCreateProduct() {
        // Write your test here
        // ARRANGE
        Product product = new Product();
        product.setTitle("Laptop");
        product.setPrice(1000.0);
        product.setDescription("Dell Laptop");
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        // ACT
        Product savedProduct = selfProductService.createProduct(product);

        // ASSERT
        Assertions.assertThat(savedProduct).isNotNull();

    }

    @Test
    public void testUpdateProduct() {
        // Write your test here
        // ARRANGE
        Product product = Mockito.mock(Product.class);
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        // ACT
        Product updatedProduct = selfProductService.updateProduct(1L, product);

        // ASSERT
        Assertions.assertThat(updatedProduct).isNotNull();


    }

    @Test
    public void testReplaceProduct(){
        // Write your test here

        // ARRANGE
        Product product = Mockito.mock(Product.class);
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));


        //when(product.getTitle()).thenReturn("Laptop");
        //when(product.getPrice()).thenReturn(1000.0);
        //when(product.getDescription()).thenReturn("Dell Laptop");
        //when(product.getImage()).thenReturn("image.jpg");

        Category category = Mockito.mock(Category.class);
        when(product.getCategory()).thenReturn(category);
        when(category.getName()).thenReturn("Electronics");

        when(categoryRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(category));
        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);
        //when(category.getId()).thenReturn(1L);
        //when(category.getName()).thenReturn("Electronics");


        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        // ACT
        Product replacedProduct = selfProductService.replaceProduct(1L, product);

        // ASSERT
        Assertions.assertThat(replacedProduct).isNotNull();

    }

    @Test
    public void testDeleteProduct() {
        // Write your test here
        Product product = Mockito.mock(Product.class);
        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product));
        assertAll(() -> selfProductService.deleteProduct(1L));
    }

    @Test
    public void testGetAllCategories() {
        // Write your test here
        // ARRANGE
        List<Category> categories = Mockito.mock(List.class);
        when(categoryRepository.findAll()).thenReturn(categories);

        // ACT
        List<Category> allCategories = selfProductService.getAllCategories();

        // ASSERT
        Assertions.assertThat(allCategories).isNotNull();
    }
    
    

}
