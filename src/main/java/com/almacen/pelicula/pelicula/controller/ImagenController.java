package com.almacen.pelicula.pelicula.controller;

import com.almacen.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.dto.in.ImagenCreate;
import com.almacen.pelicula.pelicula.entity.Imagen;
import com.almacen.pelicula.pelicula.service.ImagenService;
import com.almacen.pelicula.pelicula.service.PeliculaService;
import com.almacen.pelicula.pelicula.service.TamanoImagen;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pelicula/imagen")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Validated
public class ImagenController {

    ImagenService imagenes;

    PeliculaService peliculas;

    @GetMapping("/pequena/{idPelicula}")
    public ResponseEntity<byte[]> getImagenPequena(@PathVariable("idPelicula") Long idPelicula) {

        Imagen imagen = peliculas.recuperarImagen(idPelicula, TamanoImagen.SMALL).orElseThrow(() -> new ResourceNotFoundException("No existe la imagen"));

        var res = imagenes.buscarImagen(imagen);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(res.imagen().getType()));

        return new ResponseEntity<>(res.contenido(), headers, HttpStatus.OK);
    }

    @GetMapping("/grande/{idPelicula}")
    public ResponseEntity<byte[]> getImagenGrande(@PathVariable("idPelicula") Long idPelicula) {

        Imagen imagen = peliculas.recuperarImagen(idPelicula, TamanoImagen.LARGE).orElseThrow(() -> new ResourceNotFoundException("No existe la imagen"));

        var res = imagenes.buscarImagen(imagen);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(res.imagen().getType()));

        return new ResponseEntity<>(res.contenido(), headers, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> agregarImagenes(@PathVariable("id") Long idPelicula, @Valid ImagenCreate imagen) {

        var p = peliculas.findByID(idPelicula);
        var inicioNombre = String.format("%s_", p.titulo());
        var finNombre = String.format("_%d", p.id());

        imagen.imagenPequena().ifPresent(pequena -> imagenes.guardarImagen(pequena, inicioNombre + "SMALL" + finNombre, TamanoImagen.SMALL));

        imagen.imagenGrande().ifPresent(grande -> imagenes.guardarImagen(grande, inicioNombre + "LARGE" + finNombre, TamanoImagen.LARGE));

        return null;
    }
}
