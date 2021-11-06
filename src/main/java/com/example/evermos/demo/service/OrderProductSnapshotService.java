package com.example.evermos.demo.service;

import com.example.evermos.demo.errors.GlobalException;
import com.example.evermos.demo.models.Order;
import com.example.evermos.demo.models.OrderProductSnapshot;
import com.example.evermos.demo.models.Product;
import com.example.evermos.demo.repository.OrderProductSnapshotRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class OrderProductSnapshotService {
    private final OrderProductSnapshotRepository orderProductSnapshotRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public OrderProductSnapshotService(OrderProductSnapshotRepository productSnapshotRepository) {
        this.orderProductSnapshotRepository = productSnapshotRepository;
    }

    public Mono<OrderProductSnapshot> create(Order order, Product product) {
        try {
            return orderProductSnapshotRepository.create(new OrderProductSnapshot(null, order.getId(), mapper.writeValueAsString(product)));
        } catch (JsonProcessingException e) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
