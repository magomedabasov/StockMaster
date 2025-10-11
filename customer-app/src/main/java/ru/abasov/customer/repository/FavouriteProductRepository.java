package ru.abasov.customer.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.abasov.customer.entity.FavouriteProduct;

public interface FavouriteProductRepository {

    Mono<FavouriteProduct> save(FavouriteProduct favouriteProduct);

    Mono<Void> deleteByProductId(int productId);

    Mono<FavouriteProduct> findByProductId(int productId);

    Flux<FavouriteProduct> findAllFavouriteProducts();
}
