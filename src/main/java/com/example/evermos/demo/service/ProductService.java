package com.example.evermos.demo.service;

import com.example.evermos.demo.models.Product;
import com.example.evermos.demo.repository.ProductRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    public Mono<Product> update(Product product) {
        return productRepository.update(product);
    }

    public Flux<Product> findAll() {
        return productRepository.findAll();
    }
}
