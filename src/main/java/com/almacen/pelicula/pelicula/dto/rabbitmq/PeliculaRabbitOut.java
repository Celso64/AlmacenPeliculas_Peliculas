package com.almacen.pelicula.pelicula.dto.rabbitmq;

import com.almacen.pelicula.event.Claseable;
import com.almacen.pelicula.pelicula.entity.Pelicula;

public record PeliculaRabbitOut(Long id, String titulo, Double precio) implements Claseable {

    private static final String CLASS_NAME = "Pelicula";

    public static PeliculaRabbitOut fromModel(Pelicula p) {
        return new PeliculaRabbitOut(p.getId(), p.getTitulo(), p.getPrecio());
    }

    @Override
    public String getClassName() {
        return CLASS_NAME;
    }
}
