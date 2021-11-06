package com.example.evermos.demo.service;

import com.example.evermos.demo.controller.request.OrderRequest;
import com.example.evermos.demo.models.Order;
import com.example.evermos.demo.models.OrderProductSnapshot;
import com.example.evermos.demo.models.Product;
import com.example.evermos.demo.repository.OrderRepository;
import com.example.evermos.demo.utils.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderProductSnapshotService orderProductSnapshotService;

    public OrderService(OrderRepository orderRepository, ProductService productService, OrderProductSnapshotService orderProductSnapshotService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderProductSnapshotService = orderProductSnapshotService;
    }

    public Mono<Order> checkout(OrderRequest request) {
        return productService.findById(request.getProductId())
                .map(products -> {
                    products.validateStock(request.getQty());
                    return products;
                })
                .flatMap(products ->
                        orderRepository.getNextInsertId().flatMap(nextId -> {
                            LocalDateTime now = LocalDateTime.now();
                            Helper helper = new Helper();
                            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                            Integer month = Integer.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));
                            Integer year = Integer.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yy")));
                            String trxFormat = "TRX-" + currentDate + "-" + helper.toRoman(month) + "-" + helper.toRoman(year) + "-" + nextId;
                            return orderRepository.create(
                                    new Order(
                                            null,
                                            products.getId(),
                                            products.getPrice() * request.getQty(),
                                            request.getQty(),
                                            trxFormat,
                                            now,
                                            now
                                    )
                            );
                        }).flatMap(order -> {
                            Product product = new Product.Builder(products).setReservedQty(request.getQty()+products.getReservedQty()).build();
                            return productService.update(product)
                                    .flatMap(p -> orderProductSnapshotService.create(order, p).thenReturn(order));
                        })

                );
    }

    public Mono<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }
}
