package ru.abasov.catalogue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestController;
import ru.abasov.catalogue.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;
    private final MessageSource messageSource;


}
