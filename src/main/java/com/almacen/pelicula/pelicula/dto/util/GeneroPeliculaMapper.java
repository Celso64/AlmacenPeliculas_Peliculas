package com.almacen.pelicula.pelicula.dto.util;

import com.almacen.pelicula.pelicula.entity.GeneroPelicula;

import java.util.Map;

public class GeneroPeliculaMapper {

    private static final Map<String, GeneroPelicula> generos = Map.of(
            "accion", GeneroPelicula.ACCION,
            "drama", GeneroPelicula.DRAMA,
            "romance", GeneroPelicula.ROMANCE,
            "terror", GeneroPelicula.TERROR,
            "basada_en_hechos_reales", GeneroPelicula.BASADA_EN_HECHOS_REALES
    );


    public static GeneroPelicula map(String g){
        return generos.get(g);
    }
}
