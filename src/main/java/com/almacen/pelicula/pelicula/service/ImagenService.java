package com.almacen.pelicula.pelicula.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImagenService {

    void guardarImagen(MultipartFile imagen, Long idPelicula, TamanoImagen tamano);

    //ImagenOut buscarImagen(Imagen imagen) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}
