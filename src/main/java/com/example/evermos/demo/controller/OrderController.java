package com.example.evermos.demo.controller;

import com.example.evermos.demo.controller.metadata.EmptyMetadata;
import com.example.evermos.demo.controller.request.OrderRequest;
import com.example.evermos.demo.controller.response.BaseResponse;
import com.example.evermos.demo.controller.to.OrderTO;
import com.example.evermos.demo.models.Order;
import com.example.evermos.demo.service.OrderService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Mono<BaseResponse<OrderTO, EmptyMetadata>> index(@RequestBody OrderRequest request) {
        return orderService.checkout(request).map(order -> new BaseResponse<>(order.toOrderTO(), EmptyMetadata.empty()));
    }

    @GetMapping("/{id}")
    public Mono<BaseResponse<OrderTO, EmptyMetadata>> detail(@PathVariable("id") Integer id) {
        return orderService.findById(id).map(order -> new BaseResponse<>(order.toOrderTO(), EmptyMetadata.empty()));
    }
}
