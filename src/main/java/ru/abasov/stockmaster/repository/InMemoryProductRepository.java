package ru.abasov.stockmaster.repository;

import org.springframework.stereotype.Repository;
import ru.abasov.stockmaster.entity.Product;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = Collections.synchronizedList(new LinkedList<>());

    public InMemoryProductRepository() {
        IntStream.range(1, 10)
                .forEach(i -> this.products.add(new Product(
                        i, "Товар №%d".formatted(i),
                        "Описание товара №%d".formatted(i))));
    }

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
}
