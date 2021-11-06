package com.example.evermos.demo;

import com.example.evermos.demo.models.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    @Test
    void testHaveStockSuccess() {
        boolean thrown = false;

        try {
            product().validateStock(10);
        } catch (Exception e) {
            thrown = true;
        }

        assertFalse(thrown);
    }

    @Test
    void testHaveStockFailed() {
        boolean thrown = false;

        try {
            product().validateStock(11);
        } catch (Exception e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    void testProductOutOfStock() {
        assertTrue(productOutStok().hasOutOfStock());
    }

    @Test
    void testProductNoOutOfStock() {
        assertFalse(product().hasOutOfStock());
    }

    Product product() {
        return new Product(null, "Rolex", "Rolex", 100000.0, 10, 0, LocalDateTime.now(), LocalDateTime.now());
    }

    Product productOutStok() {
        return new Product(null, "Rolex", "Rolex", 100000.0, 0, 0, LocalDateTime.now(), LocalDateTime.now());
    }
}
