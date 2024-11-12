package com.almacen.pelicula.pelicula.controller;

import com.almacen.pelicula.pelicula.dto.in.GeneroCreate;
import com.almacen.pelicula.pelicula.entity.Genero;
import com.almacen.pelicula.pelicula.service.GeneroService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/genero")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GeneroController {

    GeneroService generoService;

    @GetMapping
    public ResponseEntity<Set<Genero>> generos() {
        return ResponseEntity.ok(generoService.generos());
    }

    @PostMapping
    @PreAuthorize("hasRole('write_pelicula')")
    public ResponseEntity<Genero> agregarGenero(@Valid @RequestBody GeneroCreate genero) {
        var body = generoService.agregarGenero(genero);
        return ResponseEntity.ok(body);
    }

}
