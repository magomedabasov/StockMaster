package ru.abasov.customer.controller.payload;

import jakarta.validation.constraints.*;

public record NewProductReviewPayload(

        @NotNull(message = "{customer.products.reviews.create.errors.rating_is_null}")
        @Min(value = 1, message = "{customer.products.reviews.create.errors.rating_is_below_min}")
        @Max(value = 5, message = "{customer.products.reviews.create.errors.rating_is_above_max}")
        Integer rating,

        @Size(max = 1000, message = "{customer.products.reviews.create.errors.review_is_too_big}")
        String review) {
}
