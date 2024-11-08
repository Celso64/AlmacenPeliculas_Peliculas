package com.almacen.pelicula.ranking.dto.in.rabbitmq;

import com.almacen.pelicula.ranking.entity.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public record UsuarioCreate(String clientId, String firstName, String lastName, String email) {

    public static UsuarioCreate fromMap(Map<String, Object> datos) throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();

        log.info("UC Se recibio data: {}", datos.toString());

        String id = ((Map<String, String>) datos.get("authDetails")).get("clientId");
        log.info("UC Se leyo ID: {}", id);

        log.info("UC Data por leerse: {}", datos.get("representation").toString());
        JsonNode jn = om.readTree(datos.get("representation").toString());


        String fName = jn.path("firstName").asText();
        String lName = jn.path("lastName").asText();
        String email = jn.path("email").asText();

        return new UsuarioCreate(id, fName, lName, email);
    }

    public Usuario toModel() {
        return new Usuario(clientId, firstName, lastName, email);
    }
}
