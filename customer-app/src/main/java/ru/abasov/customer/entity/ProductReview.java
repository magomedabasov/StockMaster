package ru.abasov.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview {

    private UUID uuid;

    private int productId;

    private int rating;

    private String review;

}
