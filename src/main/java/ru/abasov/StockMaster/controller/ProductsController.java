package ru.abasov.StockMaster.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.abasov.StockMaster.service.ProductService;

@Controller
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;
}
