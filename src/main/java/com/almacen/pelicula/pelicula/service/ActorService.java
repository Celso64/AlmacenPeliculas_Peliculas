package com.almacen.pelicula.pelicula.service;

import com.almacen.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.dto.in.ActorCreate;
import com.almacen.pelicula.pelicula.dto.in.ActorUpdate;
import com.almacen.pelicula.pelicula.dto.out.ActorOut;

import java.util.List;

public interface ActorService {

    /**
     * Crea un Actor.
     * @param actor Los datos del actor: nombre(String); apellido(String).
     * @return Los datos del actor creado.
     */
    ActorOut crearActor(ActorCreate actor);

    /**
     * Lista todos los actores.
     * @return Lista de todos los actores.
     */
    List<ActorOut> listAll();

    /**
     * Busca un actor por su id.
     * @param idActor ID del actor.
     * @return El actor, si existe.
     * @throws ResourceNotFoundException Si el ID no existe.
     */
    ActorOut findByID(Long idActor);

    /**
     * Actualiza datos de un actor.
     * @param idActor ID del actor.
     * @param nuevosDatos Datos actualizados del actor(opcionales): nombre(String); apellido(String).
     * @return El actor con datos actualizados.
     * @throws ResourceNotFoundException Si el ID del actor no existe.
     */
    ActorOut update(Long idActor, ActorUpdate nuevosDatos);

    /**
     * Borra un actor.
     * @param idActor ID del actor.
     * @return True si lo pudo borrar; False sino.
     */
    Boolean borrar(Long idActor);
}
