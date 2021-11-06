package com.example.evermos.demo.controller.to;

public class ProductTO {
    private final Integer id;
    private final String title;
    private final String description;
    private final Double price;
    private final Integer qty;
    private final Integer reservedQty;
    private final Boolean hasOutOfStock;
    private final String createdAt;
    private final String updatedAt;

    public ProductTO(Integer id, String title, String description, Double price, Integer qty, Integer reservedQty, Boolean hasOutOfStock, String createdAt, String updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.reservedQty = reservedQty;
        this.hasOutOfStock = hasOutOfStock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQty() {
        return qty;
    }

    public Integer getReservedQty() {
        return reservedQty;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Boolean getHasOutOfStock() {
        return hasOutOfStock;
    }
}
