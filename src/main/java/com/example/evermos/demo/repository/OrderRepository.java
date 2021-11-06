package com.example.evermos.demo.repository;

import com.example.evermos.demo.models.Order;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Component
public class OrderRepository extends AbstractRepository {
    private final ProductRepository productRepository;
    private final OrderProductSnapshotRepository orderProductSnapshotRepository;

    public OrderRepository(MeterRegistry meterRegistry, R2dbcEntityTemplate template, ProductRepository productRepository, OrderProductSnapshotRepository orderProductSnapshotRepository) {
        super(meterRegistry, template, "OrderRepository");
        this.productRepository = productRepository;
        this.orderProductSnapshotRepository = orderProductSnapshotRepository;
    }

    public Mono<Order> findById(Integer id) {
        return query("getById", template ->
                template.select(Order.class)
                        .matching(Query.query(Criteria.where("id").is(id)))
                        .first()
                        .flatMap(this::mapProperties).switchIfEmpty(entityEmpty()));
    }

    public Mono<Order> create(Order order) {
        return query("create", template ->
                template.insert(order).flatMap(this::mapProperties));
    }

    /**
     * get NextId from table
     *
     * @return Mono<Integer>
     */
    public Mono<BigInteger> getNextInsertId() {
        return super.getNextInsertId("orders");
    }

    public Mono<Order> mapProperties(Order order) {
        return Mono.just(order)
                .flatMap(this::mapProduct)
                .flatMap(this::mapOrderProductSnapshot);
    }

    public Mono<Order> mapProduct(Order order) {
        return productRepository.findById(order.getProductId()).map(product -> {
            order.setProduct(product);
            return order;
        }).switchIfEmpty(Mono.just(order));
    }

    public Mono<Order> mapOrderProductSnapshot(Order order) {
        return orderProductSnapshotRepository.findByOrderId(order.getId()).map(product -> {
            order.setSnapshot(product);
            return order;
        }).switchIfEmpty(Mono.just(order));
    }
}
