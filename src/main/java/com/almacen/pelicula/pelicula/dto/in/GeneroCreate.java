package com.almacen.pelicula.pelicula.dto.in;

import com.almacen.pelicula.pelicula.entity.Genero;
import jakarta.validation.constraints.NotEmpty;

public record GeneroCreate(@NotEmpty String nombre) {


    public Genero toModel() {
        var nuevo = new Genero();
        nuevo.setNombre(nombre);
        return nuevo;
    }
}
