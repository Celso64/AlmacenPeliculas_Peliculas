package com.almacen.pelicula.pelicula.service;

import com.almacen.pelicula.pelicula.dto.in.GeneroCreate;
import com.almacen.pelicula.pelicula.entity.Genero;

import java.util.Set;

public interface GeneroService {

    Set<Genero> generos();

    Genero agregarGenero(GeneroCreate genero);
}
