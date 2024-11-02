package com.almacen.pelicula.ranking.listener;

import com.almacen.pelicula.event.Event;
import com.almacen.pelicula.event.Listener;
import com.almacen.pelicula.ranking.dto.in.UsuarioCreate;
import com.almacen.pelicula.ranking.entity.Usuario;
import com.almacen.pelicula.ranking.repository.UsuarioRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UsuarioListener implements Listener<String, UsuarioCreate> {

    UsuarioRepository usuarioRepository;

    @Override
    public void escuchar(Event<String, UsuarioCreate> evento) {

        var data = evento.getData().toModel();

        switch (evento.getEventType()) {
            case CREATE:
                usuarioRepository.save(data);
                log.info("CREATE Usuario {} FROM rabbitmq", data.getId());
                break;
            case UPDATE:
                Usuario u = usuarioRepository.findById(data.getId()).orElseThrow(() -> new RuntimeException("El usuario no existe"));
                usuarioRepository.save(u);
                log.info("UPDATE Usuario {}.", data.getId());
                break;
            case DELETE:
                usuarioRepository.deleteById(data.getId());
                log.info("DELETE Usuario {} FROM rabbitmq", data.getId());
                break;
            default:
                throw new RuntimeException("Error al procesar el mensaje");
        }
    }
}
