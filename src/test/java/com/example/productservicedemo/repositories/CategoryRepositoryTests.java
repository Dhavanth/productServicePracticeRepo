package com.example.productservicedemo.repositories;

import com.example.productservicedemo.models.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testSaveCategory() {
        //ARRANGE OR GIVEN
        Category category = new Category();
        category.setName("Electronics");

        //ACT OR WHEN
        Category savedCategory = categoryRepository.save(category);

        //ASSERT OR THEN
        Assertions.assertThat(savedCategory).isNotNull();
        Assertions.assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindCategoryByName() {
        //ARRANGE OR GIVEN
        Category category = new Category();
        category.setName("Electronics");

        Category savedCategory = categoryRepository.save(category);

        //ACT OR WHEN
        Category category1 = categoryRepository.findByName(savedCategory.getName()).get();

        //ASSERT OR THEN
        Assertions.assertThat(category1).isNotNull();
        Assertions.assertThat(category1.getName()).isNotEmpty();
    }
}
