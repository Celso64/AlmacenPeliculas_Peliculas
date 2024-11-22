package com.almacen.pelicula.pelicula.controller;

import com.almacen.pelicula.pelicula.dto.in.ImagenCreate;
import com.almacen.pelicula.pelicula.service.ImagenService;
import com.almacen.pelicula.pelicula.service.TamanoImagen;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pelicula/imagen")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Validated
@Slf4j
public class ImagenController {

    ImagenService imagenes;

    @PostMapping("/{id}")
    public ResponseEntity<Void> agregarImagenes(@PathVariable("id") Long idPelicula, @Valid ImagenCreate imagen) {

        imagen.imagenPequena().ifPresent(pequena -> imagenes.guardarImagen(pequena, idPelicula, TamanoImagen.SMALL));
        imagen.imagenGrande().ifPresent(grande -> imagenes.guardarImagen(grande, idPelicula, TamanoImagen.LARGE));

        return ResponseEntity.ok().build();
    }
}
