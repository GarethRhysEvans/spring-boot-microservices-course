package com.devit.bookstore.catalog.web.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.devit.bookstore.catalog.AbstractIT;
import com.devit.bookstore.catalog.domain.Product;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-data.sql")
class ProductControllerTest extends AbstractIT {

    @Test
    void shouldReturnProducts() {

        restClient
                .get()
                .uri("/api/products")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType("application/json")
                .expectBody()
                .jsonPath("$.data.length()")
                .isEqualTo(10)
                .jsonPath("$.totalElements")
                .isEqualTo(15)
                .jsonPath("$.pageNumber")
                .isEqualTo(1)
                .jsonPath("$.totalPages")
                .isEqualTo(2)
                .jsonPath("$.isFirst")
                .isEqualTo(true)
                .jsonPath("$.isLast")
                .isEqualTo(false)
                .jsonPath("$.hasNext")
                .isEqualTo(true)
                .jsonPath("$.hasPrevious")
                .isEqualTo(false);
    }

    @Test
    void shouldGetProductByCode() {
        Product product = restClient
                .get()
                .uri("/api/products/{code}", "P100")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType("application/json")
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();

        assertThat(product.code()).isEqualTo("P100");
        assertThat(product.name()).isEqualTo("The Hunger Games");
        assertThat(product.description()).isEqualTo("Winning will make you famous. Losing means certain death...");
        assertThat(product.price()).isEqualTo(new BigDecimal("34.0"));
    }

    @Test
    void shouldReturnNotFoundWhenProductCodeNotExists() {
        String code = "invalid_product_code";

        restClient
                .get()
                .uri("/api/products/{code}", code)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectHeader()
                .contentType("application/problem+json")
                .expectBody()
                .jsonPath("$.title")
                .isEqualTo("Product Not Found")
                .jsonPath("$.detail")
                .isEqualTo("Product with code " + code + " not found");
    }
}
