package com.almacen.pelicula.pelicula.dto.out;

import com.almacen.pelicula.pelicula.entity.Genero;

public record GeneroOut(Long id, String nombre) {
    public static GeneroOut fromModel(Genero g) {
        return new GeneroOut(g.getId(), g.getNombre());
    }
}
