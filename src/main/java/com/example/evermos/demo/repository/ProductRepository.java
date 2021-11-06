package com.example.evermos.demo.repository;

import com.example.evermos.demo.errors.GlobalException;
import com.example.evermos.demo.models.Product;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductRepository extends AbstractRepository {


    public ProductRepository(MeterRegistry meterRegistry, R2dbcEntityTemplate template) {
        super(meterRegistry, template, "ProductRepository");
    }

    public Mono<Product> findById(Integer id) {
        return query("getById", template ->
                template.select(Product.class)
                        .matching(Query.query(Criteria.where("id").is(id)))
                        .first()
                        .switchIfEmpty(entityEmpty()));
    }

    public Flux<Product> findAll() {
        return query("getById", template ->
                template.select(Product.class).all().switchIfEmpty(entityEmpty()));
    }

    public Mono<Product> update(Product product) {
        return query("update", template ->
                template.update(product));
    }
}
