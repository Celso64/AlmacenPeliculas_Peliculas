package com.almacen.pelicula.pelicula.controller;

import com.almacen.pelicula.pelicula.dto.in.DirectorCreate;
import com.almacen.pelicula.pelicula.dto.in.DirectorUpdate;
import com.almacen.pelicula.pelicula.dto.out.DirectorOut;
import com.almacen.pelicula.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.service.DirectorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/director")
public class DirectorController {

    @Autowired
    private DirectorService directores;


    @GetMapping
    public ResponseEntity<List<DirectorOut>> listAll(){
        return ResponseEntity.ok(directores.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorOut> buscarPorId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(directores.buscarPorID(id));
    }

    @PostMapping
    public ResponseEntity<DirectorOut> crearDirector(@Valid @RequestBody DirectorCreate director){
        return ResponseEntity.ok(directores.crearDirector(director));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectorOut> updateDirector(@PathVariable("id") Long id,
                                      @Valid @RequestBody DirectorUpdate director) throws ResourceNotFoundException {

        DirectorOut res = directores.update(id, director);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarDirector(@PathVariable("id") Long id){
        if(directores.borrarDirector(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
