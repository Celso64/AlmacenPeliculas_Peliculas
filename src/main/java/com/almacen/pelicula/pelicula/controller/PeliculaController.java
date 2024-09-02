package com.almacen.pelicula.pelicula.controller;

import com.almacen.pelicula.pelicula.dto.out.PeliculaOut;
import com.almacen.pelicula.pelicula.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pelicula")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculas;

    @GetMapping
    public Map<String, Object> listarPeliculas(@RequestParam(defaultValue = "0") int pagina){
        Page<PeliculaOut> p = peliculas.listPeliculas(pagina);

        Map<String, Object> response = new HashMap<>();
        response.put("data", p.getContent());
        response.put("pag_actual", p.getNumber());
        response.put("total_items", p.getTotalElements());
        response.put("total_pag", p.getTotalPages());

        return response;
    }
}
