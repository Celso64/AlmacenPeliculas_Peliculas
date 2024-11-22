package com.almacen.pelicula.event;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageProducer {

    final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.event.exchange.eventos}")
    String eventExchange;

    public <K, T> void publishEvent(Event<K, T> event, String routingKey) {
        rabbitTemplate.convertAndSend(eventExchange, event.getRoutingkey(routingKey), event);
    }
}
