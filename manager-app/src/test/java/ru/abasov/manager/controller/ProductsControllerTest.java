package ru.abasov.manager.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @Test
    @DisplayName("createProduct при валидном запросе создаёт новый товар и перенаправляет на его страницу")
    void createProduct_RequestIsValid_ReturnRedirectToProductPage() {
        // given
        String title = "Товар";
        String details = "Описание";

        var payload = new NewProductPayload(title, details);
        var createdProduct = new Product(1, title, details);

        when(this.productsRestClient.createProduct(payload.title(), payload.details()))
                .thenReturn(createdProduct);

        // when
        String result = this.controller.createProduct(payload, this.model);

        // then
        verify(this.productsRestClient).createProduct(payload.title(), payload.details());
        verifyNoMoreInteractions(this.productsRestClient);
        verifyNoInteractions(this.model);

        assertEquals("redirect:/catalogue/products/1", result);
    }

    @Test
    @DisplayName("createProduct при невалидном запросе возвращает страницу с ошибками")
    void createProduct_RequestIsInvalid_ReturnProductFormWithErrors() {
        // given

        var payload = new NewProductPayload("   ", null);
        List<String> expectedErrors = List.of("title is blank", "details is null");

        doThrow(new BadRequestException(expectedErrors))
                .when(this.productsRestClient)
                .createProduct(payload.title(), payload.details());

        // when
        String result = this.controller.createProduct(payload, this.model);

        // then
        assertEquals("catalogue/products/new_product", result);

        verify(this.model).addAttribute("errors", expectedErrors);
        verify(this.model).addAttribute(eq("payload"), same(payload));
        verify(this.productsRestClient).createProduct(payload.title(), payload.details());
        verifyNoMoreInteractions(this.productsRestClient);
        verifyNoMoreInteractions(this.model);

    }

}