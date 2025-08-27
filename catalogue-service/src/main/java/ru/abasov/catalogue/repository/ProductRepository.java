package ru.abasov.catalogue.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.abasov.catalogue.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query(value = "select * from catalogue.t_product where c_title ilike :filter", nativeQuery = true)
    Iterable<Product> findAllByTitleLikeIgnoreCase(@Param("filter") String filter);
}