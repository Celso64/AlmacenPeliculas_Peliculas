package com.almacen.pelicula.pelicula.controller;

import com.almacen.pelicula.pelicula.dto.in.ActorCreate;
import com.almacen.pelicula.pelicula.dto.in.ActorUpdate;
import com.almacen.pelicula.pelicula.dto.out.ActorOut;
import com.almacen.pelicula.pelicula.service.ActorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actor")
public class ActorController {

    @Autowired
    private ActorService actores;

    @GetMapping
    @PreAuthorize("hasRole('read_pelicula')")
    public ResponseEntity<List<ActorOut>> listAll(){
        return ResponseEntity.ok(actores.listAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('read_pelicula')")
    public ResponseEntity<ActorOut> buscarPorID(@PathVariable("id") Long id){
        return ResponseEntity.ok(actores.findByID(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('write_pelicula')")
    public ResponseEntity<ActorOut> crear(@Valid @RequestBody ActorCreate actor){
        return ResponseEntity.ok(actores.crearActor(actor));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('write_pelicula')")
    public ResponseEntity<ActorOut> update(@PathVariable("id") Long idActor, @Valid @RequestBody ActorUpdate actor){
        return ResponseEntity.ok(actores.update(idActor, actor));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('write_pelicula')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long idActor){
        return actores.borrar(idActor) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
