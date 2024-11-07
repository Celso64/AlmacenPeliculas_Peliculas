package com.almacen.pelicula.pelicula.dto.rabbitmq;

import com.almacen.pelicula.pelicula.entity.Pelicula;

import java.util.Set;
import java.util.stream.Collectors;

public record PeliculaRabbitOut(Long id, String titulo, Double precio, Set<String> actores, Set<String> directores) {

    private static final String CLASS_NAME = "Pelicula";

    public static PeliculaRabbitOut fromModel(Pelicula p) {

        var actores = p.getActores()
                .stream()
                .map(a -> a.getNombre() + " " + a.getApellido())
                .collect(Collectors.toSet());

        var directores = p.getDirectores()
                .stream()
                .map(d -> d.getNombre() + " " + d.getApellido())
                .collect(Collectors.toSet());

        return new PeliculaRabbitOut(p.getId(), p.getTitulo(), p.getPrecio(), actores, directores);
    }

    public String getClassName() {
        return CLASS_NAME;
    }
}
