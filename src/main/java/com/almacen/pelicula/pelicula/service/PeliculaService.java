package com.almacen.pelicula.pelicula.service;

import com.almacen.pelicula.pelicula.dto.in.PeliculaCreate;
import com.almacen.pelicula.pelicula.dto.out.PeliculaOut;
import com.almacen.pelicula.pelicula.entity.Pelicula;
import com.almacen.pelicula.pelicula.repository.DirectorRepository;
import com.almacen.pelicula.pelicula.repository.PeliculaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculas;

    @Autowired
    private DirectorRepository directores;

    @Value("${tam_page_pelicula}")
    private Integer tamanoPagina;

    public Page<PeliculaOut> listPeliculas(Integer pagina){

        Page<Pelicula> paginaPeliculas = peliculas.findAll(PageRequest.of(pagina, tamanoPagina));

        List<PeliculaOut> peliculasOut = paginaPeliculas
                .getContent()
                .stream()
                .map(PeliculaOut::fromModel)
                .toList();

        return new PageImpl<>(peliculasOut, PageRequest.of(pagina, tamanoPagina), paginaPeliculas.getTotalElements());
    }

    public void crearPelicula(PeliculaCreate pelicula){
        Pelicula p = pelicula.toModel(directores);
        peliculas.save(p);
    }
}
