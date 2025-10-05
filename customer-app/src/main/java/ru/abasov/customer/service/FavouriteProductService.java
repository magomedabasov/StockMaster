package ru.abasov.customer.service;

import reactor.core.publisher.Mono;
import ru.abasov.customer.entity.FavouriteProduct;

public interface FavouriteProductService {

    Mono<FavouriteProduct> addProductToFavourite(int productId);

    Mono<Void> removeProductFromFavourite(int productId);

    Mono<FavouriteProduct> findFavouriteProductByProduct(int productId);
}
