package com.almacen.pelicula.pelicula.controller;

import com.almacen.pelicula.pelicula.dto.in.PeliculaCreate;
import com.almacen.pelicula.pelicula.dto.out.PeliculaMinOut;
import com.almacen.pelicula.pelicula.dto.out.PeliculaOut;
import com.almacen.pelicula.pelicula.service.PeliculaService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pelicula")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Validated
public class PeliculaController {

    @Autowired
    PeliculaService peliculas;

    @GetMapping
    public Map<String, Object> listarPeliculas(@RequestParam(defaultValue = "0") int pagina) {
        Page<PeliculaMinOut> p = peliculas.listPeliculas(pagina);

        Map<String, Object> response = new HashMap<>();
        response.put("data", p.getContent());
        response.put("pag_actual", p.getNumber());
        response.put("total_items", p.getTotalElements());
        response.put("total_pag", p.getTotalPages());

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaOut> findByID(@PathVariable("id") Long id) {
        return ResponseEntity.ok(peliculas.findByID(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('write_pelicula')")
    public ResponseEntity<PeliculaOut> crearPelicula(@Valid @RequestBody PeliculaCreate pelicula) throws IOException {
        return ResponseEntity.ok(peliculas.crearPelicula(pelicula));
    }
}
