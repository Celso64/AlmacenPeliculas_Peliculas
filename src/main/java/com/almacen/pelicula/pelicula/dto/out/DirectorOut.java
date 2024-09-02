package com.almacen.pelicula.pelicula.dto.out;

import com.almacen.pelicula.pelicula.entity.Director;

public record DirectorOut(Long id, String nombre, String apellido) {

    public static DirectorOut fromModel(Director d){
        return new DirectorOut(d.getId(), d.getNombre(), d.getApellido());
    }
}
