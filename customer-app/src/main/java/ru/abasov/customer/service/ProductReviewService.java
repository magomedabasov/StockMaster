package ru.abasov.customer.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.abasov.customer.entity.ProductReview;

public interface ProductReviewService {

    Mono<ProductReview> save(int productId, int rating, String review);

    Flux<ProductReview> findAllByProductId(int productId);
}
