package com.example.evermos.demo.controller;

import com.example.evermos.demo.controller.metadata.EmptyMetadata;
import com.example.evermos.demo.controller.response.BaseResponse;
import com.example.evermos.demo.controller.response.BaseResponseList;
import com.example.evermos.demo.controller.to.ProductTO;
import com.example.evermos.demo.models.Product;
import com.example.evermos.demo.service.ProductService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Mono<BaseResponseList<ProductTO, EmptyMetadata>> index() {
        return productService.findAll().collectList()
                .map(product ->
                        new BaseResponseList<>(
                                product.stream().map(Product::toProductTO).collect(Collectors.toList()),
                                EmptyMetadata.empty()
                        )
                );
    }

    @GetMapping("/{id}")
    public Mono<BaseResponse<ProductTO, EmptyMetadata>> detail(@PathVariable("id") Integer id) {
        return productService.findById(id).map(product -> new BaseResponse<>(product.toProductTO(), EmptyMetadata.empty()));
    }
}
