package com.almacen.pelicula.pelicula.dto.in;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public record ImagenCreate(Optional<MultipartFile> imagenPequena,
                           Optional<MultipartFile> imagenGrande) {


}
