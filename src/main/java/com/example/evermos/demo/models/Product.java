package com.example.evermos.demo.models;

import com.example.evermos.demo.controller.to.ProductTO;
import com.example.evermos.demo.errors.GlobalException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Table("products")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Product {
    @Id
    private final Integer id;
    private final String title;
    private final String description;
    private final Double price;
    private final Integer qty;
    private final Integer reservedQty;
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDateTime createdAt;
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDateTime updatedAt;

    public Product(
            Integer id,
            String title,
            String description,
            Double price,
            Integer qty,
            Integer reservedQty,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.reservedQty = reservedQty;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static class Builder {
        private Integer reservedQty;
        private final Product product;

        public Builder(Product product) {
            this.product = product;
        }

        public Builder setReservedQty(Integer reservedQty) {
            this.reservedQty = reservedQty;
            return this;
        }

        public Product build() {
            return new Product(
                    product.getId(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getQty(),
                    reservedQty,
                    product.getCreatedAt(),
                    LocalDateTime.now()
            );
        }
    }

    public ProductTO toProductTO() {
        return new ProductTO(id,title,description,price,qty,reservedQty, hasOutOfStock(), createdAt.toString(), updatedAt.toString());
    }

    public Boolean hasOutOfStock() {
        return (qty - reservedQty) <= 0;
    }

    public void validateStock(Integer qty) {
        if (hasOutOfStock() || qty > this.qty - reservedQty) {
            throw new GlobalException(
                    HttpStatus.BAD_REQUEST,
                    title + " is out of stock",
                    null
            );
        }
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
