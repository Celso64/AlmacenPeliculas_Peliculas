package com.almacen.pelicula.pelicula.controller;

import com.almacen.pelicula.pelicula.dto.in.ActorCreate;
import com.almacen.pelicula.pelicula.dto.in.ActorUpdate;
import com.almacen.pelicula.pelicula.dto.out.ActorOut;
import com.almacen.pelicula.pelicula.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actor")
public class ActorController {

    @Autowired
    private ActorService actores;

    @GetMapping
    public ResponseEntity<List<ActorOut>> listAll(){
        return ResponseEntity.ok(actores.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorOut> buscarPorID(@PathVariable("id") Long id){
        return ResponseEntity.ok(actores.findByID(id));
    }

    @PostMapping
    public ResponseEntity<ActorOut> crear(@RequestBody ActorCreate actor){
        return ResponseEntity.ok(actores.crearActor(actor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorOut> update(@PathVariable("id") Long idActor, @RequestBody ActorUpdate actor){
        return ResponseEntity.ok(actores.update(idActor, actor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long idActor){
        return actores.borrar(idActor) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
