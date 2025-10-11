package ru.abasov.customer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.abasov.customer.client.ProductsClient;
import ru.abasov.customer.controller.payload.NewProductReviewPayload;
import ru.abasov.customer.entity.Product;
import ru.abasov.customer.service.FavouriteProductService;
import ru.abasov.customer.service.ProductReviewService;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("customer/products/{productId:\\d+}")
public class ProductController {

    private final ProductsClient productsClient;

    private final FavouriteProductService favouriteProductService;

    private final ProductReviewService productReviewService;

    @ModelAttribute(name = "product", binding = false)
    public Mono<Product> loadProduct(@PathVariable("productId") int productId) {
        return this.productsClient.findProduct(productId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("customer.products.error.not_found")));

    }

    @ModelAttribute("inFavourite")
    public Mono<Boolean> loadInFavourite(@PathVariable("productId") int productId) {
        return this.favouriteProductService.findFavouriteProductByProduct(productId)
                .map(fav -> true)
                .defaultIfEmpty(false);
    }

    @GetMapping
    public Mono<String> getProductPage(@PathVariable("productId") int productId, Model model) {
        return this.productReviewService.findAllByProductId(productId)
                .collectList()
                .doOnNext(productReviews -> model.addAttribute("reviews", productReviews))
                .thenReturn("customer/products/product");
    }


    @PostMapping("add-to-favourites")
    public Mono<String> addProductToFavourites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> this.favouriteProductService.addProductToFavourite(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId)));
    }

    @PostMapping("remove-from-favourites")
    public Mono<String> removeProductFromFavourites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> this.favouriteProductService.removeProductFromFavourite(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId)));
    }

    @PostMapping("create-review")
    public Mono<String> createReview(@PathVariable("productId") int productId,
                                     @Valid NewProductReviewPayload payload,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .peek(System.out::println)
                    .toList());
            return Mono.just("customer/products/product");
        } else {
            return this.productReviewService.save(productId, payload.rating(), payload.review())
                    .thenReturn("redirect:/customer/products/%d".formatted(productId));
        }
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "errors/404";
    }
}
