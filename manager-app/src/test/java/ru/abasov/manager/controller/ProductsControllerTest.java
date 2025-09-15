package ru.abasov.manager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.abasov.manager.client.BadRequestException;
import ru.abasov.manager.client.ProductsRestClient;
import ru.abasov.manager.controller.payload.NewProductPayload;
import ru.abasov.manager.entity.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Модульные тесты ProductsController")
class ProductsControllerTest {

    @Mock
    ProductsRestClient productsRestClient;

    @Mock
    Model model;

    @InjectMocks
    ProductsController controller;

    @BeforeEach


    @Test
    @DisplayName("createProduct создаёт новый товар и перенаправляет на его страницу")
    void createProduct_RequestIsValid_ReturnRedirectToProductPage() {
        // given
        String title = "Товар";
        String details = "Описание";

        var payload = new NewProductPayload(title, details);
        var createdProduct = new Product(1, title, details);

        when(this.productsRestClient.createProduct(title, details))
                .thenReturn(createdProduct);

        // when
        String result = this.controller.createProduct(payload, model);

        // then
        verify(productsRestClient).createProduct(title, details);
        verifyNoMoreInteractions(productsRestClient);
        verifyNoInteractions(model);

        assertEquals("redirect:/catalogue/products/1", result);
        System.out.println(model);
    }

    @Test
    @DisplayName("createProduct возвращает страницу с ошибками при невалидном запросе")
    void createProduct_RequestIsInvalid_ReturnProductFormWithErrors() {
        // given

        var payload = new NewProductPayload("   ", null);
        var model = new ConcurrentModel();

        doThrow(new BadRequestException(List.of("title is blank", "details is null")))
                .when(this.productsRestClient)
                .createProduct("   ", null);

        // when
        String result = this.controller.createProduct(payload, model);

        // then
        assertEquals("catalogue/products/new_product", result);
        assertEquals(payload, model.getAttribute("payload"));
        assertEquals(List.of("title is blank", "details is null"), model.getAttribute("errors"));

        verify(this.productsRestClient).createProduct("   ", null);
        verifyNoMoreInteractions(this.productsRestClient);

    }

}