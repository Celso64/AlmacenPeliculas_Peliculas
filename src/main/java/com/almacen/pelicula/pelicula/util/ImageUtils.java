package com.almacen.pelicula.pelicula.util;

import com.almacen.pelicula.pelicula.service.TamanoImagen;
import org.springframework.stereotype.Component;

@Component
public class ImageUtils {

    public String generarNombre(Long id, TamanoImagen tamanoImagen) {
        return id + "_" + tamanoImagen.name();
    }
}
