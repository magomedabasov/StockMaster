package ru.abasov.customer.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.abasov.customer.entity.ProductReview;

public interface ProductReviewRepository {

    Mono<ProductReview> save(ProductReview productReview);

    Flux<ProductReview> findAllByProductId(int productId);
}
