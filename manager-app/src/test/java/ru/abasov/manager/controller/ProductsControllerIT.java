package ru.abasov.manager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class ProductsControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "manager", roles = "MANAGER")
    void getNewProductPage_ReturnsProductPage() throws Exception {
        //given
        var mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/catalogue/products/create");

        //when
        this.mockMvc.perform(mockHttpServletRequestBuilder)

        //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name("catalogue/products/new_product")
                );
    }
}
