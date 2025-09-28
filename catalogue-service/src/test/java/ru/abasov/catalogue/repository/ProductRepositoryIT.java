package ru.abasov.catalogue.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.abasov.catalogue.entity.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("standalone")
class ProductRepositoryIT {

    @Autowired
    ProductRepository productRepository;

    @Test
    @Sql(value = "/sql/products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findAllByTitleLikeIgnoreCase_ReturnsFilteredProducts() {
        //given
        var filter = "ошибочное";

        List<Product> expectedProducts = List.of(
                new Product(3, "Ошибочное название №3", "Ошибочное описание №3"),
                new Product(4, "Ошибочное название №4", "Ошибочное описание №4"));

        //when
        var actualProducts = productRepository.findAllByTitleLikeIgnoreCase(filter);

        //then
        assertNotNull(actualProducts);
        assertEquals(expectedProducts, actualProducts);
    }
}