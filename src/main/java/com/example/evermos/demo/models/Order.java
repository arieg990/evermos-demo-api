package com.example.evermos.demo.models;

import com.example.evermos.demo.controller.to.OrderTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("orders")
public class Order {
    @Id
    private final Integer id;
    private final Integer productId;
    private final Double price;
    private final Integer qty;
    private final String orderNumber;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    @Transient
    private Product product;
    @Transient
    private OrderProductSnapshot snapshot;

    public Order(Integer id, Integer productId, Double price, Integer qty, String orderNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {
        System.out.println(orderNumber);
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.qty = qty;
        this.orderNumber = orderNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public OrderTO toOrderTO() {
        return new OrderTO(id, productId, price, qty, orderNumber, createdAt.toString(), updatedAt.toString(), product.toProductTO(), snapshot);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderProductSnapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(OrderProductSnapshot snapshot) {
        this.snapshot = snapshot;
    }
}
