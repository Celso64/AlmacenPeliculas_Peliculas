package com.almacen.pelicula.pelicula.dto.in;

import com.almacen.pelicula.pelicula.entity.Director;
import jakarta.validation.constraints.NotBlank;

/**
 * Representa el DTO para crear un Director.
 * @param nombre Obligatorio.
 * @param apellido Obligatorio.
 *
 * @author Carlos Farra
 * @since Domingo 1 de Septiembre 2024
 */
public record DirectorCreate(@NotBlank(message = "Nombre obligatorio.") String nombre,
                             @NotBlank(message = "Apellido obligatorio.") String apellido) {

    public Director toModel(){
        Director nuevo = new Director();
        nuevo.setNombre(nombre);
        nuevo.setApellido(apellido);

        return nuevo;
    }
}
