package org.devit.bookstore.orders.clients.catalog;

import org.devit.bookstore.orders.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class CatalogServiceClientConfig {
    @Bean
    RestClient restClient(ApplicationProperties properties) {

        return RestClient.builder().baseUrl(properties.catalogServiceUrl())
                .build();
    }
}