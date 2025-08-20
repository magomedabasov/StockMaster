package ru.abasov.StockMaster.repository;

import org.springframework.stereotype.Repository;
import ru.abasov.StockMaster.entity.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = Collections.synchronizedList(new LinkedList<>());
}
