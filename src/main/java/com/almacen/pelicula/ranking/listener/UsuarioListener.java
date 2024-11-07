package com.almacen.pelicula.ranking.listener;

import com.almacen.pelicula.event.Event;
import com.almacen.pelicula.event.Listener;
import com.almacen.pelicula.ranking.dto.in.rabbitmq.Representation;
import com.almacen.pelicula.ranking.repository.UsuarioRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UsuarioListener implements Listener<String, Representation> {

    UsuarioRepository usuarioRepository;

    @Override
    public void escuchar(Event<String, Representation> evento) {

        var data = evento.getData().toModel();
        data.setIdKeyLoack(evento.getKey());

        log.info("Usuario: {}", evento.getKey());

        if (Objects.requireNonNull(evento.getEventType()) == Event.Type.CREATE) {
            log.info("Usuario por crearse...");
            usuarioRepository.save(data);
            log.info("CREATE Usuario {} FROM rabbitmq", data.getId());
        } else {
            log.info("Caso no contemplado: {}", evento.getEventType());
        }
    }
}
