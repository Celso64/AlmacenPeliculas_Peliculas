package com.almacen.pelicula.pelicula.service;

import com.almacen.pelicula.pelicula.dto.out.ImagenOut;
import com.almacen.pelicula.pelicula.entity.Imagen;
import org.springframework.web.multipart.MultipartFile;

public interface ImagenService {

    void guardarImagen(MultipartFile imagen, String nombreImagen, TamanoImagen tamano);

    ImagenOut buscarImagen(Imagen imagen);
}
