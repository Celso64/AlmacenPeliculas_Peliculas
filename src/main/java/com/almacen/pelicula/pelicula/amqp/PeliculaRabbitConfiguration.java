package com.almacen.pelicula.pelicula.amqp;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PeliculaRabbitConfiguration {

    @Value("${rabbitmq.event.movie.queue.name}")
    String colaPeliculas;

    @Value("${rabbitmq.event.movie.routing.key}")
    String routingKey;

    @Bean
    public Queue movieQueue() {
        return new Queue(colaPeliculas, true); // true para que sea durable
    }

    @Bean
    public Binding binding(Queue movieQueue, @Qualifier("exchangeVideoCloub02") TopicExchange topicExchange) {
        return BindingBuilder
                .bind(movieQueue)
                .to(topicExchange)
                .with(routingKey);
    }
}
