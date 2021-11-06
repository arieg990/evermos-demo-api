package com.example.evermos.demo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("order_product_snapshots")
public class OrderProductSnapshot {
    @Id
    private final Integer id;
    private final Integer orderId;
    private final String product;

    public OrderProductSnapshot(Integer id, Integer orderId, String product) {
        this.id = id;
        this.orderId = orderId;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getProduct() {
        return product;
    }
}
