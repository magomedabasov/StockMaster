package ru.abasov.stockmaster.repository;

import ru.abasov.stockmaster.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
}
