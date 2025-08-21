package ru.abasov.stockmaster.service;

import ru.abasov.stockmaster.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    Product createProduct(String title, String details);
}
