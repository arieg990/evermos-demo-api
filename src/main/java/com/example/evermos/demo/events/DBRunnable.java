package com.example.evermos.demo.events;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

@FunctionalInterface
public interface DBRunnable<T> {
    public abstract T run(R2dbcEntityTemplate template);
}
