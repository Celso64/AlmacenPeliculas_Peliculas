package com.almacen.pelicula.pelicula.amqp;

import com.almacen.pelicula.event.Event;
import com.almacen.pelicula.pelicula.dto.rabbitmq.PeliculaRabbitOut;

public class PeliculaEvent extends Event<Long, PeliculaRabbitOut> {

    public PeliculaEvent(Type eventType, PeliculaRabbitOut data) {
        super(eventType, data.id(), data);
    }
}
