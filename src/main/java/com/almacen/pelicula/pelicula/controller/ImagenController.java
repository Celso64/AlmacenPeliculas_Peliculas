package com.almacen.pelicula.pelicula.controller;

import com.almacen.pelicula.pelicula.dto.in.ImagenCreate;
import com.almacen.pelicula.pelicula.service.ImagenService;
import com.almacen.pelicula.pelicula.service.TamanoImagen;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/pelicula/imagen")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImagenController {

    @Autowired
    ImagenService imagenes;

    @GetMapping("/pequena/{idPelicula}")
    public ResponseEntity<byte[]> getImagenPequena(@PathVariable("idPelicula") Long idPelicula) throws IOException {

        var res = imagenes.buscarImagen(idPelicula, TamanoImagen.SMALL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(res.imagen().getType()));

        return new ResponseEntity<>(res.contenido(), headers, HttpStatus.OK);
    }

    @GetMapping("/grande/{idPelicula}")
    public ResponseEntity<byte[]> getImagenGrande(@PathVariable("idPelicula") Long idPelicula) throws IOException {

        var res = imagenes.buscarImagen(idPelicula, TamanoImagen.LARGE);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(res.imagen().getType()));

        return new ResponseEntity<>(res.contenido(), headers, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> agregarImagenes(@PathVariable("id") Long idPelicula, ImagenCreate imagen) {
        return imagenes.guardarImagenes(idPelicula, imagen) ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
    }
}
