package ru.abasov.catalogue.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.abasov.catalogue.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query(value = "select p from Product p where p.title ilike concat('%', :filter, '%')")
    Iterable<Product> findAllByTitleLikeIgnoreCase(@Param("filter") String filter);
}