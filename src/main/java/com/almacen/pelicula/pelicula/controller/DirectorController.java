package com.almacen.pelicula.pelicula.controller;

import com.almacen.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.dto.in.DirectorCreate;
import com.almacen.pelicula.pelicula.dto.in.DirectorUpdate;
import com.almacen.pelicula.pelicula.dto.out.DirectorOut;
import com.almacen.pelicula.pelicula.service.DirectorService;
import com.almacen.pelicula.pelicula.service.impl.DirectorServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/director")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DirectorController {

    @Autowired
    DirectorService directores;


    @GetMapping
    @PreAuthorize("hasRole('read_pelicula')")
    public ResponseEntity<List<DirectorOut>> listAll() {
        return ResponseEntity.ok(directores.listar());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('read_pelicula')")
    public ResponseEntity<DirectorOut> buscarPorId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(directores.buscarPorID(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('write_pelicula')")
    public ResponseEntity<DirectorOut> crearDirector(@Valid @RequestBody DirectorCreate director) {
        return ResponseEntity.status(HttpStatus.CREATED).body(directores.crearDirector(director));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('write_pelicula')")
    public ResponseEntity<DirectorOut> updateDirector(@PathVariable("id") Long id,
                                                      @Valid @RequestBody DirectorUpdate director) {
        return ResponseEntity.ok(directores.update(id, director));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('write_pelicula')")
    public ResponseEntity<Void> borrarDirector(@PathVariable("id") Long id) {
        return directores.borrarDirector(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
