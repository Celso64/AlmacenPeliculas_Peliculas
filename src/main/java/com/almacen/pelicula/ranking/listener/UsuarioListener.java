package com.almacen.pelicula.ranking.listener;

import com.almacen.pelicula.event.Event;
import com.almacen.pelicula.event.Listener;
import com.almacen.pelicula.ranking.dto.in.rabbitmq.UsuarioCreate;
import com.almacen.pelicula.ranking.dto.in.rabbitmq.UsuarioDelete;
import com.almacen.pelicula.ranking.entity.Usuario;
import com.almacen.pelicula.ranking.repository.UsuarioRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UsuarioListener implements Listener<String, UsuarioCreate> {

    UsuarioRepository usuarioRepository;

    @Override
    public void create(Event<String, UsuarioCreate> evento) {
        var data = evento.getData().toModel();
        data.setIdKeyLoack(evento.getKey());

        log.info("Usuario por crearse...");
        usuarioRepository.save(data);
        log.info("CREATE Usuario {} FROM rabbitmq", data.getId());
    }

    @Override
    public void delete(Event<String, UsuarioDelete> evento) {
        Optional<Usuario> user = usuarioRepository.findOneByIdKeyLoack(evento.getData().clientId());
        user.ifPresent(u -> usuarioRepository.deleteById(u.getId()));
        if (user.isEmpty()) log.info("Se intento borrar a {} pero no existe en la DB", evento.getData().clientId());
    }
}
