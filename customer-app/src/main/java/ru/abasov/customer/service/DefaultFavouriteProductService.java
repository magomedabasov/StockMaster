package ru.abasov.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.abasov.customer.entity.FavouriteProduct;
import ru.abasov.customer.repository.FavouriteProductRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultFavouriteProductService implements FavouriteProductService {

    private final FavouriteProductRepository favouriteProductRepository;

    @Override
    public Mono<FavouriteProduct> addProductToFavourite(int productId) {
        return this.favouriteProductRepository.save(new FavouriteProduct(UUID.randomUUID(), productId));
    }

    @Override
    public Mono<Void> removeProductFromFavourite(int productId) {
        return this.favouriteProductRepository.deleteByProductId(productId);
    }

    @Override
    public Mono<FavouriteProduct> findFavouriteProductByProduct(int productId) {
        return this.favouriteProductRepository.findByProductId(productId);
    }
}
