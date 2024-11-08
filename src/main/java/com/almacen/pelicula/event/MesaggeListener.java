package com.almacen.pelicula.event;

import com.almacen.pelicula.pelicula.entity.Pelicula;
import com.almacen.pelicula.ranking.dto.in.rabbitmq.UsuarioCreate;
import com.almacen.pelicula.ranking.dto.in.rabbitmq.UsuarioDelete;
import com.almacen.pelicula.ranking.listener.UsuarioListener;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.Map;

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
    @Retryable(maxAttempts = 4, backoff = @Backoff(delay = 5000))
    public void handleUsuarioEvent(Map<String, Object> mensaje) throws JsonProcessingException {
        log.info("Handle Usuario");
        log.info("Data: {}", mensaje.toString());

        if (mensaje.containsKey("operationType")) {
            switch ((String) mensaje.get("operationType")) {
                case "CREATE":
                    var userCreate = UsuarioCreate.fromMap(mensaje);
                    usuarioListener.create(new Event<>(Event.Type.CREATE, userCreate.clientId(), userCreate));
                    break;
                case "DELETE":
                    var userDelete = UsuarioDelete.fromMap(mensaje);
                    usuarioListener.delete(new Event<>(Event.Type.DELETE, userDelete.clientId(), userDelete));
                    break;
            }
        }
    }

    @Recover
    public void recover(Exception e, Event<String, Pelicula> event) {
        log.info(event.getData().toString());
    }
}
