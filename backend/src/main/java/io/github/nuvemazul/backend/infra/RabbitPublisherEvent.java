package io.github.nuvemazul.backend.infra;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.github.nuvemazul.backend.dtos.CreateGameEvent;

@Component
public class RabbitPublisherEvent implements PublisherEvent {

    private RabbitTemplate rabbitTemplate;
    private String exchange;
    private String routingKey;

    public RabbitPublisherEvent(
            RabbitTemplate rabbitTemplate,
            @Value("${rabbitmq.exchange.name}") String exchange,
            @Value("${rabbitmq.routing.key}") String routingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @Override
    public void publish(CreateGameEvent event) {
        rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                event
        );
    }
}