package com.almacen.pelicula.pelicula.service;

import com.almacen.pelicula.pelicula.dto.in.ActorCreate;
import com.almacen.pelicula.pelicula.dto.in.ActorUpdate;
import com.almacen.pelicula.pelicula.dto.out.ActorOut;
import com.almacen.pelicula.pelicula.entity.Actor;
import com.almacen.pelicula.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actores;

    public ActorOut crearActor(ActorCreate actor){
        Actor nuevoActor = actor.toModel();
        return ActorOut.fromModel(actores.save(nuevoActor));
    }

    public List<ActorOut> listAll(){
        return actores.findAll()
                .stream()
                .map(ActorOut::fromModel)
                .toList();
    }

    public ActorOut findByID(Long idActor){
        Actor actor = actores.findById(idActor).orElseThrow(() -> new ResourceNotFoundException("El actor no existe."));
        return ActorOut.fromModel(actor);
    }

    public ActorOut update(Long idActor, ActorUpdate nuevosDatos){

        Actor actorExistente = actores.findById(idActor)
                .orElseThrow(() -> new ResourceNotFoundException("El actor no existe."));

        Actor actorActualizado = nuevosDatos.update(actorExistente);
        return ActorOut.fromModel(actores.save(actorActualizado));
    }

    public Boolean borrar(Long idActor){
        if(actores.existsById(idActor)){
            actores.deleteById(idActor);
            return true;
        }
        return false;
    }
}
