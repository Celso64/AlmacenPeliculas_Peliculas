package com.almacen.pelicula.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class RabbitConfiguration {

    @Value("${rabbitmq.event.exchange.name}")
    String eventExchange;


    private String exchangeName() {
        log.info("EXCHANGE: {}", eventExchange);
        return eventExchange;
    }

    @Bean
    public TopicExchange exchangeVideoCloub02() {
        return new TopicExchange(exchangeName());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
