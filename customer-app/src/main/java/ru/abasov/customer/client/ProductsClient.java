package ru.abasov.customer.client;

import reactor.core.publisher.Flux;
import ru.abasov.customer.entity.Product;

public interface ProductsClient {

    Flux<Product> findAllProducts(String filter);
}
