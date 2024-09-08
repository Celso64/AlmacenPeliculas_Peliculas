package com.almacen.pelicula.pelicula.dto;

import com.almacen.pelicula.pelicula.dto.out.DirectorOut;

public record DirectorTest(String nombre, String apellido) {
    public String toJSON(){
        return String.format("{\"nombre\" : \"%s\", \"apellido\" : \"%s\"}", nombre, apellido);
    }
}
