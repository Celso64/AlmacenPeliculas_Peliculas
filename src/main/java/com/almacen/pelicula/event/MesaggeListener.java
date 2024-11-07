package com.almacen.pelicula.event;

import com.almacen.pelicula.pelicula.entity.Pelicula;
import com.almacen.pelicula.ranking.dto.in.UsuarioRabbit;
import com.almacen.pelicula.ranking.listener.UsuarioListener;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class MesaggeListener {

    UsuarioListener usuarioListener;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "${rabbitmq.event.usuario.queue.name}",
                    durable = "true"
            ),
            exchange = @Exchange(value = "${rabbitmq.event.exchange.users}", type = "topic"),
            key = "${rabbitmq.event.usuario.routing.key}"
    ))
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public void handleUsuarioEvent(UsuarioRabbit mensaje) throws IOException {
        log.info("Handle Usuario");
        log.info("Data: {}", mensaje.toString());
        if (Objects.nonNull(mensaje.operationType()) && mensaje.operationType().equalsIgnoreCase(Event.Type.CREATE.toString()))
            usuarioListener.escuchar(mensaje.toEvent());
    }

    @Recover
    public void recover(Exception e, Event<String, Pelicula> event) {
        log.info(event.getData().toString());
    }
}
