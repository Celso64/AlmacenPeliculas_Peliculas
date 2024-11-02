package com.almacen.pelicula.pelicula.amqp;

import com.almacen.pelicula.event.Event;
import com.almacen.pelicula.event.MessageProducer;
import com.almacen.pelicula.pelicula.dto.rabbitmq.PeliculaRabbitOut;
import com.almacen.pelicula.pelicula.entity.Pelicula;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class PeliculaPublish {

    MessageProducer peliculaProducer;

    private String getRoutingKey() {
        return "Pelicula.";
    }

    @PostPersist
    private void creandoPelicula(Pelicula p) {
        var salida = PeliculaRabbitOut.fromModel(p);
        Event<Long, PeliculaRabbitOut> evento = new Event<>(Event.Type.CREATE, p.getId(), salida);
        peliculaProducer.publishEvent(evento, getRoutingKey());
        log.info("CREATE Pelicula {} enviado a rabbitmq", p.getId());
    }

    @PostUpdate
    private void actualizandoPelicula(Pelicula p) {
        var salida = PeliculaRabbitOut.fromModel(p);
        Event<Long, PeliculaRabbitOut> evento = new Event<>(Event.Type.UPDATE, p.getId(), salida);
        peliculaProducer.publishEvent(evento, getRoutingKey());
        log.info("UPDATE Pelicula {} enviado a rabbitmq", p.getId());
    }

    @PostRemove
    private void eliminandoPelicula(Pelicula p) {
        var salida = PeliculaRabbitOut.fromModel(p);
        Event<Long, PeliculaRabbitOut> evento = new Event<>(Event.Type.DELETE, p.getId(), salida);
        peliculaProducer.publishEvent(evento, getRoutingKey());
        log.info("DELETE Pelicula {} enviado a rabbitmq", p.getId());
    }
}
