package com.almacen.pelicula.pelicula.service;

import com.almacen.pelicula.pelicula.dto.in.ImagenCreate;
import com.almacen.pelicula.pelicula.dto.out.ImagenOut;
import com.almacen.pelicula.pelicula.entity.Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImagenService {

    Boolean guardarImagenes(Long idPelicula, ImagenCreate imagenes);

    Imagen guardarImagen(MultipartFile imagen, String folder) throws IOException;

    byte[] buscarImagen(Imagen imagenData) throws IOException;

    ImagenOut buscarImagen(Long idPelicula, TamanoImagen tamImagen) throws IOException;
}
