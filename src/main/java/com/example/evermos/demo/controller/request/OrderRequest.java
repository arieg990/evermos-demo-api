package com.example.evermos.demo.controller.request;

public class OrderRequest {
    final Integer productId;
    final Integer qty;

    public OrderRequest(Integer productId, Integer qty) {
        this.productId = productId;
        this.qty = qty;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getQty() {
        return qty;
    }
}
