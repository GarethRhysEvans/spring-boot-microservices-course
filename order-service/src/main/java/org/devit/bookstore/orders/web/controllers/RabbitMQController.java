package org.devit.bookstore.orders.web.controllers;

import org.devit.bookstore.orders.ApplicationProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RabbitMQController {

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    RabbitMQController(RabbitTemplate rabbitTemplate, ApplicationProperties applicationProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = applicationProperties;

    }

    @PostMapping(path = "/send")
    public void sendMessage(@RequestBody MyMessage message ) {
        rabbitTemplate.convertAndSend(
                properties.orderEventsExchange(),
                message.routeKey(),
                message.payload()
        );
    }

    record MyMessage(String routeKey, MyPayload payload) {}

    record MyPayload(String content) {}
}
