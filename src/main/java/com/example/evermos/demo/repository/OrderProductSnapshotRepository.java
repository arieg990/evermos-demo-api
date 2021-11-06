package com.example.evermos.demo.repository;

import com.example.evermos.demo.models.OrderProductSnapshot;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class OrderProductSnapshotRepository extends AbstractRepository {

    public OrderProductSnapshotRepository(MeterRegistry meterRegistry, R2dbcEntityTemplate template) {
        super(meterRegistry, template, "OrderRepository");
    }

    public Mono<OrderProductSnapshot> findByOrderId(Integer id) {
        return query("findByOrderId", template ->
                template.select(OrderProductSnapshot.class)
                        .matching(Query.query(Criteria.where("order_id").is(id)))
                        .first());
    }

    public Mono<OrderProductSnapshot> create(OrderProductSnapshot orderProductSnapshot) {
        return query("create", template ->
                template.insert(orderProductSnapshot));
    }
}
