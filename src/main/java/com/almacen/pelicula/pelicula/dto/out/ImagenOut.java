package com.almacen.pelicula.pelicula.dto.out;

import com.almacen.pelicula.pelicula.entity.Imagen;

public record ImagenOut(Imagen imagen, byte[] contenido) {
}
