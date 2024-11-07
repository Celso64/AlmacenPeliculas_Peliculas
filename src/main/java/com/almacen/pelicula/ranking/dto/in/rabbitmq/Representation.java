package com.almacen.pelicula.ranking.dto.in.rabbitmq;

import com.almacen.pelicula.ranking.entity.Usuario;

import java.util.List;

public record Representation(String firstName, String lastName, String email, List<String> groups) {

    public Usuario toModel() {
        return new Usuario(firstName, lastName, email);
    }
}
