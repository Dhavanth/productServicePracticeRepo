package com.example.productservicedemo.repositories;

import com.example.productservicedemo.models.Category;
import com.example.productservicedemo.models.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testSaveProduct() {
        //ARRANGE OR GIVEN
        Product product1 = new Product();
        product1.setTitle("Laptop");
        product1.setPrice(1000.0);
        product1.setDescription("Dell Laptop");

        //ACT OR WHEN
        Product savedproduct = productRepository.save(product1);

        //ASSERT OR THEN
        Assertions.assertThat(savedproduct).isNotNull();
        Assertions.assertThat(savedproduct.getId()).isGreaterThan(0);

    }

    @Test
    public void testFindProductById() {
        //ARRANGE OR GIVEN
        Product product1 = new Product();
        product1.setTitle("Laptop");
        product1.setPrice(1000.0);
        product1.setDescription("Dell Laptop");

        Product savedproduct = productRepository.save(product1);

        //ACT OR WHEN
        Product product = productRepository.findById(savedproduct.getId()).orElse(null);

        //ASSERT OR THEN
        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getId()).isEqualTo(savedproduct.getId());
    }

    @Test
    public void testFindByCategoryName() {
        //ARRANGE OR GIVEN
        Product product1 = new Product();
        product1.setTitle("Laptop");
        product1.setPrice(1000.0);
        product1.setDescription("Dell Laptop");

        Product product2 = new Product();
        product2.setTitle("Sound Bar");
        product2.setPrice(10000.0);
        product2.setDescription("SONY Sound Bar");

        Category category = new Category();
        category.setName("Electronics");
        Category savedCategory = categoryRepository.save(category);

        product1.setCategory(savedCategory);
        product2.setCategory(savedCategory);

        Product savedproduct = productRepository.save(product1);

        //ACT OR WHEN
        List<Product> products = productRepository.findByCategory_Name("Electronics");

        //ASSERT OR THEN
        Assertions.assertThat(products).isNotNull();
        Assertions.assertThat(products.size()).isEqualTo(2);
    }

    @Test
    public void testDeleteProduct() {
        //ARRANGE OR GIVEN
        Product product1 = new Product();
        product1.setTitle("Laptop");
        product1.setPrice(1000.0);
        product1.setDescription("Dell Laptop");

        Product savedproduct = productRepository.save(product1);

        //ACT OR WHEN
        productRepository.deleteById(savedproduct.getId());

        //ASSERT OR THEN
        Product product = productRepository.findById(savedproduct.getId()).orElse(null);
        Assertions.assertThat(product).isNull();
    }
}
