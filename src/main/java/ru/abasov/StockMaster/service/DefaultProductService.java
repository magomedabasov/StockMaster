package ru.abasov.StockMaster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.abasov.StockMaster.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;
}
