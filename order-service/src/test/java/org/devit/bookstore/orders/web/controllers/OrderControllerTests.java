package org.devit.bookstore.orders.web.controllers;

import org.devit.bookstore.orders.AbstractIT;
import org.devit.bookstore.orders.testdata.TestDataFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.springframework.http.MediaType.APPLICATION_JSON;


public class OrderControllerTests extends AbstractIT {

    @Nested
    class CreateOrderTests {

        @Test
        void shouldCreateOrderSuccessfully() {
            var payload =
                    """
                        {
                            "customer" : {
                                "name": "Siva",
                                "email": "siva@gmail.com",
                                "phone": "999999999"
                            },
                            "deliveryAddress" : {
                                "addressLine1": "HNO 123",
                                "addressLine2": "Kukatpally",
                                "city": "Hyderabad",
                                "state": "Telangana",
                                "zipCode": "500072",
                                "country": "India"
                            },
                            "items": [
                                {
                                    "code": "P100",
                                    "name": "Product 1",
                                    "price": 25.50,
                                    "quantity": 1
                                }
                            ]
                        }
                    """;

            restClient.post()
                    .uri("/api/orders")
                    .contentType(APPLICATION_JSON)
                    .body(payload)
                    .exchange()
                    .expectStatus().is2xxSuccessful()
                    .expectBody().jsonPath("orderNumber").isNotEmpty();


        }
        @Test
        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
            var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();

            restClient.post()
                    .uri("/api/orders")
                    .contentType(APPLICATION_JSON)
                    .body(payload)
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody().jsonPath("errors").isNotEmpty();
        }

    }
}
