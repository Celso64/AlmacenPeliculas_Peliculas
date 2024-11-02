package com.almacen.pelicula.event;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageProducer {

    final AmqpTemplate amqpTemplate;
    final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.event.exchange.name}")
    String eventExchange;
    @Value("${rabbitmq.event.movie.routing.key}")
    String routingKey;

    public <K, T> void publishEvent(Event<K, T> event, String routingKey) {
//        amqpTemplate.convertAndSend(eventExchange, event.getRoutingkey(), event);
        rabbitTemplate.convertAndSend(eventExchange, event.getRoutingkey(routingKey), event);
    }
}
