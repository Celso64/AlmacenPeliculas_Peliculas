package com.almacen.pelicula.ranking.dto.in;

import com.almacen.pelicula.ranking.entity.Usuario;

import java.util.HashSet;

public record UsuarioCreate(Long idUsuario, String nombre) {

    public Usuario toModel() {
        var nuevoUser = new Usuario();
        nuevoUser.setId(idUsuario);
        nuevoUser.setUsername(nombre);
        nuevoUser.setResenas(new HashSet<>());
        return nuevoUser;
    }
}
