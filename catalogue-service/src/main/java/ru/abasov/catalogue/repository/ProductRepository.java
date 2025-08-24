package ru.abasov.catalogue.repository;

import org.springframework.data.repository.CrudRepository;
import ru.abasov.catalogue.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
