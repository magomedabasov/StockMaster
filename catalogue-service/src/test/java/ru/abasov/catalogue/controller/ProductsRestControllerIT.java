package ru.abasov.catalogue.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testcontainers")
class ProductsRestControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql(value = "/sql/products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findProducts_ReturnsProductsList() throws Exception {
        //given
        var mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/catalogue-api/products")
                .param("filter", "товар")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));

        //when
        mockMvc.perform(mockHttpServletRequestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                {"id": 1, "title": "Товар №1", "details":  "Описание товара №1"},
                                {"id": 2, "title": "Товар №2", "details":  "Описание товара №2"}
                                ]
                                """
                        )
                );
    }

    @Test
    void createProduct_RequestIsValid_ReturnsNewProduct() throws Exception {
        //given
        var mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/catalogue-api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"title":  "Новый товар", "details": "Описание нового товара"}
                        """)
                .with(jwt().jwt(builder -> builder.claim("scope", "edit_catalogue")));

        //when
        mockMvc.perform(mockHttpServletRequestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string(HttpHeaders.LOCATION, "http://localhost/catalogue-api/products/1"),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json(
                                """
                                        {
                                            "id": 1,
                                            "title":  "Новый товар",
                                            "details": "Описание нового товара"
                                        }
                                        """));
    }

    @Test
    void createProduct_RequestIsInvalid_ReturnsProblemDetail() throws Exception {
        //given
        var mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/catalogue-api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .locale(new Locale("ru", "RU"))
                .content("""
                        {
                            "title":  "  ", "details": null
                        }
                        """)
                .with(jwt().jwt(builder -> builder.claim("scope", "edit_catalogue")));

        //when
        mockMvc.perform(mockHttpServletRequestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json(
                                """
                                        {
                                            "errors": [
                                            "Название товара должно быть от 3 до 50 символов"
                                            ]
                                        }
                                        """));
    }

    @Test
    void createProduct_UserIsNotAuthorized_ReturnsForbidden() throws Exception {
        //given
        var mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/catalogue-api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .locale(new Locale("ru", "RU"))
                .content("""
                        {
                            "title":  "im not", "details": "authorized"
                        }
                        """)
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));

        //when
        mockMvc.perform(mockHttpServletRequestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isForbidden());
    }
}