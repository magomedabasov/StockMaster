package ru.abasov.customer.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.abasov.customer.entity.Product;

public interface ProductsClient {

    Flux<Product> findAllProducts(String filter);

    Mono<Product> findProduct(int productId);
}
