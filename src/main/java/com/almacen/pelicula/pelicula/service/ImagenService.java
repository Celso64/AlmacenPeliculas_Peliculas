package com.almacen.pelicula.pelicula.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImagenService {
    void guardarImagen(MultipartFile imagen, Long idPelicula, TamanoImagen tamano);
}
