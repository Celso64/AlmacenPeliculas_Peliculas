package com.almacen.pelicula.pelicula.dto.in;

import com.almacen.pelicula.pelicula.entity.Director;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

import java.util.Optional;

public record DirectorUpdate(Optional<@NotBlank(message = "Nombre obligatorio.") String> nombre,
                             Optional<@NotBlank(message = "Apellido obligatorio.") String> apellido) {


    public Director toModel(Director directorExistente) {
        nombre.ifPresent(directorExistente::setNombre);
        apellido.ifPresent(directorExistente::setApellido);
        return directorExistente;
    }
}
