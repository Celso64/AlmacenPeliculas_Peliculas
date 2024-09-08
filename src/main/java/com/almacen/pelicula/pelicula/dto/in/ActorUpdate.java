package com.almacen.pelicula.pelicula.dto.in;

import com.almacen.pelicula.pelicula.entity.Actor;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record ActorUpdate(Optional<@NotBlank(message = "Nombre obligatorio.") String> nombre,
                          Optional<@NotBlank(message = "Apellido obligatorio.") String> apellido) {

    public Actor update(Actor actorExistente){
        nombre.ifPresent(actorExistente::setNombre);
        apellido.ifPresent(actorExistente::setApellido);
        return actorExistente;
    }
}
