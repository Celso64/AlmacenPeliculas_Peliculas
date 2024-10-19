package com.almacen.pelicula.pelicula.service.impl;

import com.almacen.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.dto.in.ActorCreate;
import com.almacen.pelicula.pelicula.dto.in.ActorUpdate;
import com.almacen.pelicula.pelicula.dto.out.ActorOut;
import com.almacen.pelicula.pelicula.entity.Actor;
import com.almacen.pelicula.pelicula.repository.ActorRepository;
import com.almacen.pelicula.pelicula.service.ActorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    ActorRepository actores;

    @Override
    public ActorOut crearActor(ActorCreate actor) {
        Actor nuevoActor = actor.toModel();
        return ActorOut.fromModel(actores.save(nuevoActor));
    }

    public List<ActorOut> listAll() {
        return actores.findAll()
                .stream()
                .map(ActorOut::fromModel)
                .toList();
    }

    @Override
    public ActorOut findByID(Long idActor) {
        Actor actor = actores.findById(idActor).orElseThrow(() -> new ResourceNotFoundException("El actor no existe."));
        return ActorOut.fromModel(actor);
    }

    @Override
    public ActorOut update(Long idActor, ActorUpdate nuevosDatos) {

        Actor actorExistente = actores.findById(idActor)
                .orElseThrow(() -> new ResourceNotFoundException("El actor no existe."));

        Actor actorActualizado = nuevosDatos.update(actorExistente);
        return ActorOut.fromModel(actores.save(actorActualizado));
    }

    @Override
    public Boolean borrar(Long idActor) {
        if (actores.existsById(idActor)) {
            actores.deleteById(idActor);
            return true;
        }
        return false;
    }
}
