package com.almacen.pelicula.ranking.dto.in;

import com.almacen.pelicula.event.Event;
import com.almacen.pelicula.ranking.dto.in.rabbitmq.AuthDetails;
import com.almacen.pelicula.ranking.dto.in.rabbitmq.Representation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public record UsuarioRabbit(AuthDetails authDetails, String representation, String operationType) {

    private Event.Type getType(String type) {
        return switch (type) {
            case "CREATE" -> Event.Type.CREATE;
            case "UPDATE" -> Event.Type.UPDATE;
            default -> Event.Type.DELETE;
        };
    }

    public Event<String, Representation> toEvent() throws IOException {
        System.out.println("Representation VALUE: " + representation);
        ObjectMapper mapper = new ObjectMapper();
        Representation repObj = null;
        try {
            repObj = mapper.readValue(representation, Representation.class);
        } catch (JsonProcessingException e) {
            throw new IOException("Error en el metodo toEvent()" + e.getMessage());
        }
        return new Event<>(getType(operationType), authDetails.userId(), repObj);
    }
}