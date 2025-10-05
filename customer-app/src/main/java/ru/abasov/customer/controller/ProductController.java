package ru.abasov.customer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.abasov.customer.client.ProductsClient;
import ru.abasov.customer.controller.payload.NewProductReviewPayload;
import ru.abasov.customer.entity.Product;
import ru.abasov.customer.service.FavouriteProductService;
import ru.abasov.customer.service.ProductReviewService;

@Controller
@RequiredArgsConstructor
@RequestMapping("customer/products/{productId:\\d+}")
public class ProductController {

    private final ProductsClient productsClient;

    private final FavouriteProductService favouriteProductService;

    private final ProductReviewService productReviewService;

    @ModelAttribute(name = "product", binding = false)
    public Mono<Product> loadProduct(@PathVariable("productId") int productId) {
        return this.productsClient.findProduct(productId);

    }

    @GetMapping
    public Mono<String> getProductPage(@PathVariable("productId") int productId, Model model) {
        model.addAttribute("inFavourite", false);
        return this.productReviewService.findAllByProductId(productId)
                .collectList()
                .doOnNext(productReviews -> model.addAttribute("reviews", productReviews))
                .then(this.favouriteProductService.findFavouriteProductByProduct(productId)
                        .doOnNext(favouriteProduct -> model.addAttribute("inFavourite", true)))

                .thenReturn("customer/products/product");
    }


    @PostMapping("add-to-favourites")
    public Mono<String> addProductToFavourites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> this.favouriteProductService.addProductToFavourite(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId)));
    }

    @PostMapping("delete-from-favourites")
    public Mono<String> deleteProductFromFavourites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> this.favouriteProductService.removeProductFromFavourite(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId)));
    }

    @PostMapping("create-review")
    public Mono<String> createReview(@PathVariable("productId") int productId,
                                     NewProductReviewPayload payload) {
        return this.productReviewService.save(productId, payload.rating(), payload.review())
                .thenReturn("redirect:/customer/products/%d".formatted(productId));
    }
}
