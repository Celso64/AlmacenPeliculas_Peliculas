package com.almacen.pelicula.pelicula.dto.in;

import com.almacen.pelicula.pelicula.entity.Actor;
import jakarta.validation.constraints.NotBlank;

public record ActorCreate(@NotBlank(message = "Nombre obligatorio.") String nombre,
                          @NotBlank(message = "Apellido obligatorio.") String apellido) {

    public Actor toModel(){
        Actor nuevo = new Actor();
        nuevo.setNombre(nombre);
        nuevo.setApellido(apellido);

        return nuevo;
    }
}
