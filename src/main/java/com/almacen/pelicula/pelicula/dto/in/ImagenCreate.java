package com.almacen.pelicula.pelicula.dto.in;

import com.almacen.pelicula.pelicula.dto.validation.ValidImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public record ImagenCreate(
        Optional<@ValidImage(message = "La imagen pequeña no es valida.") MultipartFile> imagenPequena,
        Optional<@ValidImage(message = "La imagen grande no es valida.") MultipartFile> imagenGrande) {
}
