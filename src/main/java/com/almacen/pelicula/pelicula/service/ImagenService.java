package com.almacen.pelicula.pelicula.service;

import com.almacen.pelicula.pelicula.dto.out.ImagenOut;
import com.almacen.pelicula.pelicula.entity.Imagen;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface ImagenService {

    void guardarImagen(MultipartFile imagen, String nombreImagen, TamanoImagen tamano);

    ImagenOut buscarImagen(Imagen imagen) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}
