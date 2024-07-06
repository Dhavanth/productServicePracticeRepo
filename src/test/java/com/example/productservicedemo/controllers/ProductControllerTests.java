package com.example.productservicedemo.controllers;

import com.example.productservicedemo.models.Category;
import com.example.productservicedemo.models.Product;
import com.example.productservicedemo.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.web.servlet.function.RequestPredicates.param;

@WebMvcTest(ProductController.class) // To setup MockMvc instance for testing ProductController only
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc; // Injects MockMvc instance to perform HTTP requests in tests

    @MockBean
    private ProductService productService; // Injected to simulate service layer

    @Autowired
    private ObjectMapper objectMapper; // Injected to convert Java objects to JSON

    private Product product;
    private Category category;

    @BeforeEach
    public void init(){
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        product = new Product();
        product.setId(1L);
        product.setTitle("Laptop");
        product.setPrice(1000.0);
        product.setCategory(category);
    }

    // Add your tests here

    @Test
    public void testAddNewProduct() throws Exception {
        given(productService.createProduct(ArgumentMatchers.any(Product.class))) // when createProduct is called with any Product object as argument
                .willAnswer((invocation) -> invocation.getArgument(0)); // willAnswer -> to specify custom answer for the mock method call
        // invocation -> the method call intercepted by the mockito
        // getArgument(0) -> gets the first argument passed to the method call

        ResultActions resultActions = mockMvc.perform(post("/products"))   // creates a mock POST request
                .contentType(MediaType.APPLICATION_JSON) // indicates the RequestBody is JSON formatted
                .content(objectMapper.writeValueAsString(product)); // converts the product object to JSON string and sets it as RequestBody

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(product.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(product.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(product.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(product.getCategory().getName()));
                //.andDo(MockMvcResultHandlers.print()); // Prints the response details

    }

    @Test
    public void testUpdateProduct() throws Exception {
        // Add your code here
        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setTitle("Updated Laptop");
        updatedProduct.setPrice(2000.0);
        updatedProduct.setCategory(category);

        given(productService.updateProduct(ArgumentMatchers.anyLong(), ArgumentMatchers.any(Product.class)))
                .willReturn(updatedProduct);

        ResultActions resultActions = mockMvc.perform(patch("/products/1"))
                .contentType(MediaType.APPLICATION_JSON) // indicates the RequestBody is JSON formatted
                .content(objectMapper.writeValueAsString(updatedProduct)); // converts the updatedProduct object to JSON string and sets it as RequestBody
        ;

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updatedProduct.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(updatedProduct.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(updatedProduct.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(updatedProduct.getCategory().getName()));

    }


    @Test
    public void testReplaceProduct() throws Exception {

        Product replacedProduct = new Product();
        replacedProduct.setId(1L);
        replacedProduct.setTitle("Replaced Laptop");
        replacedProduct.setPrice(3000.0);
        replacedProduct.setCategory(category);

        given(productService.replaceProduct(ArgumentMatchers.anyLong(), ArgumentMatchers.any(Product.class)))
                .willReturn(replacedProduct);

        ResultActions resultActions = mockMvc.perform(put("/products/1"))
                .contentType(MediaType.APPLICATION_JSON) // indicates the RequestBody is JSON formatted
                .content(objectMapper.writeValueAsString(replacedProduct)); // converts the replacedProduct object to JSON string and sets it as RequestBody
        ;

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(replacedProduct.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(replacedProduct.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(replacedProduct.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(replacedProduct.getCategory().getName()));
    }

    @Test
    public void testDeleteProduct() throws Exception {

        doNothing().when(productService.deleteProduct(1L));

        ResultActions resultActions = mockMvc.perform(delete("/products/1"));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetSingleProduct() throws Exception {

        when(productService.getSingleproduct(1L)).thenReturn(product);

        ResultActions resultActions = mockMvc.perform(get("/products/1"));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(product.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(product.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(product.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(product.getCategory().getName()));
    }

    @Test
    public void testGetAllProducts() throws Exception {

        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Mobile");
        product2.setPrice(500.0);
        product2.setCategory(category);

        Product product3 = new Product();
        product3.setId(3L);
        product3.setTitle("Tablet");
        product3.setPrice(300.0);
        product3.setCategory(category);

        Page<Product> productPage = new PageImpl<>(List.of(product, product2, product3));

        when(productService.getAllProducts(0, 3, "title", "asc")).thenReturn(productPage);

        ResultActions resultActions = mockMvc.perform(get("/products"))
                .contentType(MediaType.APPLICATION_JSON)
                        .param("pageNumber", "0")
                        .param("pageSize", "3")
                        .param("sortBy", "title")
                        .param("sortOrder", "asc");

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content.size()", CoreMatchers.is(productPage.getSize())))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void testGetAllCategories() throws Exception {
        // Add your code here

        when(productService.getAllCategories()).thenReturn(List.of(category));

        ResultActions resultActions = mockMvc.perform(get("/products/categories"));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(category.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(category.getName()));
    }

    @Test
    public void testGetProductInCategory() throws Exception{

        when(productService.getProductsInCategory("Electronics")).thenReturn(List.of(product));

        ResultActions resultActions = mockMvc.perform(get("/products/categories/Electronics"));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(product.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(product.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(product.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value(product.getCategory().getName()))
                .andDo(MockMvcResultHandlers.print());
    }



}
