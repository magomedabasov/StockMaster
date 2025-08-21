package ru.abasov.stockmaster.repository;

import org.springframework.stereotype.Repository;
import ru.abasov.stockmaster.entity.Product;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = Collections.synchronizedList(new LinkedList<>());


    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(this.products);
    }

    @Override
    public Product save(Product product) {
        product.setId(this.products.size() + 1);
        this.products.add(product);
        return product;
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        return this.products.stream().filter(product -> product.getId().equals(productId)).findFirst();
    }

    @Override
    public void deleteById(Integer id) {
        this.products.removeIf(product -> product.getId().equals(id));
    }
}
