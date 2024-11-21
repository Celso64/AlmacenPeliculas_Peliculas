package com.almacen.pelicula.pelicula.dto.out;

import com.almacen.pelicula.pelicula.entity.Actor;

public record ActorOut(Long id, String nombre, String apellido) {

    public static ActorOut fromModel(Actor actor) {
        return new ActorOut(actor.getId(), actor.getNombre(), actor.getApellido());
    }
}
