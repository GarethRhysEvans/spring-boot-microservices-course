package org.devit.bookstore.orders.web.controllers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
class RabbitMQListener {

    @RabbitListener(queues="${orders.new-orders-queue}")
    public void handleNewOrder(RabbitMQController.MyPayload payload) {
        System.out.println("New order: "+payload.content());
    }

    @RabbitListener(queues="${orders.delivered-orders-queue}")
    public void handleDeliveredOrder(RabbitMQController.MyPayload payload) {
        System.out.println("Delivered order: "+payload.content());
    }

}
