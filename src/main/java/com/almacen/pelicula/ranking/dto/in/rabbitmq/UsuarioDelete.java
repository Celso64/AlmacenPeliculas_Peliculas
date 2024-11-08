package com.almacen.pelicula.ranking.dto.in.rabbitmq;

import java.util.Map;

public record UsuarioDelete(String clientId) {

    public static UsuarioDelete fromMap(Map<String, Object> data) {
        String id = ((Map<String, String>) data.get("authDetails")).get("clientId");
        return new UsuarioDelete(id);
    }
}
