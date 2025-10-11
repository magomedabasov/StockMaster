package ru.abasov.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.abasov.customer.entity.ProductReview;
import ru.abasov.customer.repository.ProductReviewRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultProductReviewService implements ProductReviewService {

    private final ProductReviewRepository productReviewRepository;


    @Override
    public Mono<ProductReview> save(int productId, int rating, String review) {
        return this.productReviewRepository.save(
                new ProductReview(UUID.randomUUID(), productId, rating, review)
        );
    }

    @Override
    public Flux<ProductReview> findAllByProductId(int productId) {
        return this.productReviewRepository.findAllByProductId(productId);
    }
}
