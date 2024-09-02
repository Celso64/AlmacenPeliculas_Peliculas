package com.almacen.pelicula.pelicula.dto.util;

import com.almacen.pelicula.pelicula.entity.CondicionPelicula;

import java.util.Map;

public abstract class CondicionPeliculaMapper {

    private static final Map<String, CondicionPelicula> condiciones = Map.of(
            "nuevo", CondicionPelicula.NUEVO,
            "usado", CondicionPelicula.USADO
    );



    public static CondicionPelicula map(String c){
        return condiciones.get(c);
    }
}
