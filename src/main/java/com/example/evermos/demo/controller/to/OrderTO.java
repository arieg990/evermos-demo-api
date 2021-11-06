package com.example.evermos.demo.controller.to;

import com.example.evermos.demo.models.OrderProductSnapshot;

public class OrderTO {
    private final Integer id;
    private final Integer productId;
    private final Double price;
    private final Integer qty;
    private final String orderNumber;
    private final String createdAt;
    private final String updatedAt;
    private final ProductTO product;
    private final OrderProductSnapshot snapshot;

    public OrderTO(
            Integer id,
            Integer productId,
            Double price,
            Integer qty,
            String orderNumber,
            String createdAt,
            String updatedAt,
            ProductTO product,
            OrderProductSnapshot snapshot
    ) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.qty = qty;
        this.orderNumber = orderNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.product = product;
        this.snapshot = snapshot;
    }

    public Integer getId() {
        return id;
    }

    public Integer getProductId() {
        return productId;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQty() {
        return qty;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public ProductTO getProduct() {
        return product;
    }

    public OrderProductSnapshot getSnapshot() {
        return snapshot;
    }
}
