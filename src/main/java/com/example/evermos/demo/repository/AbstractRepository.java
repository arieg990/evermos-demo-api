package com.example.evermos.demo.repository;

import com.example.evermos.demo.errors.GlobalException;
import com.example.evermos.demo.events.DBRunnable;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.NoSuchElementException;

abstract class AbstractRepository {
    private final MeterRegistry meterRegistry;
    private final R2dbcEntityTemplate template;
    private final String metricPrefix;
    @Value("${spring.r2dbc.database}")
    private String databaseName;

    public AbstractRepository(MeterRegistry meterRegistry, R2dbcEntityTemplate template, String metricPrefix) {
        this.meterRegistry = meterRegistry;
        this.template = template;
        this.metricPrefix = metricPrefix;
    }

    protected <T> T query(String queryName, DBRunnable<T> function) {
        return meterRegistry.timer("repositories", "repositories", metricPrefix, "query", queryName, "type", "duration")
                .record(() -> function.run(template));
    }

    public Mono<BigInteger> getNextInsertId(String tableName) {
        System.out.println(databaseName);
        System.out.println(tableName);
        return query("getNextInsertId", temp ->
                temp.getDatabaseClient().sql("SELECT AUTO_INCREMENT as id\n" +
                                "FROM information_schema.TABLES\n" +
                                "WHERE TABLE_SCHEMA = :databaseName\n" +
                                "AND TABLE_NAME = :tableName ")
                        .bind("databaseName", databaseName)
                        .bind("tableName", tableName)
                        .fetch()
                        .one().map(row -> (BigInteger) row.get("id")));
    }

    public <T> Mono<T> entityEmpty() {
        return Mono.error(new NoSuchElementException("Entity not found"));
    }
}
