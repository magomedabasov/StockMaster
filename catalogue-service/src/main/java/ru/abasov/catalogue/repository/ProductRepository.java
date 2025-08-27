package ru.abasov.catalogue.repository;

import org.springframework.data.repository.CrudRepository;
import ru.abasov.catalogue.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    Iterable<Product> findAllByTitleLikeIgnoreCase(String filter);
}