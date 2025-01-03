package com.almacen.pelicula.pelicula.service.impl;

import com.almacen.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.dto.in.PeliculaCreate;
import com.almacen.pelicula.pelicula.dto.in.PeliculaUpdate;
import com.almacen.pelicula.pelicula.dto.out.PeliculaMinOut;
import com.almacen.pelicula.pelicula.dto.out.PeliculaOut;
import com.almacen.pelicula.pelicula.entity.*;
import com.almacen.pelicula.pelicula.repository.*;
import com.almacen.pelicula.pelicula.service.PeliculaService;
import com.almacen.pelicula.pelicula.service.TamanoImagen;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class PeliculaServiceImpl implements PeliculaService {

    final PeliculaRepository peliculas;
    final DirectorRepository directores;
    final ActorRepository actores;
    final ImagenRepository imagenes;
    final GeneroRepository generos;

//    @PersistenceContext
//    EntityManager entityManager;

    @Value("${page_tam.pelicula}")
    Integer tamanoPagina;

    @Override
    public PeliculaOut crearPelicula(PeliculaCreate pelicula) {
        List<Director> directoresPersistentes = pelicula.idsDirectores().stream()
                .map(id -> directores.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Director con ID " + id + " no encontrado.")))
                .toList();

        List<Actor> actoresPersistentes = pelicula.idsActores().stream()
                .map(id -> actores.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Actor con ID " + id + " no encontrado.")))
                .toList();


        Genero genero = generos.findById(pelicula.idGenero()).orElseThrow(() -> new ResourceNotFoundException("El genero " + pelicula.idGenero() + " no existe."));
        Pelicula p = pelicula.toModel(directoresPersistentes, actoresPersistentes, genero);

        p.setActores(new HashSet<>(actoresPersistentes));
        p.setDirectores(new HashSet<>(directoresPersistentes));
        p.setGenero(genero);

        Pelicula pr = peliculas.save(p);
        return PeliculaOut.fromModel(pr);
    }

    @Override
    public Page<PeliculaMinOut> listPeliculas(Integer pagina) {

        Page<Pelicula> paginaPeliculas = peliculas.findAll(PageRequest.of(pagina, tamanoPagina));

        paginaPeliculas.getContent().forEach(p -> {
            p.getDirectores().forEach(Object::toString);
            p.getActores().forEach(Object::toString);
        });

        List<PeliculaMinOut> peliculasOut = paginaPeliculas
                .getContent()
                .stream()
                .map(PeliculaMinOut::fromModel)
                .toList();

        return new PageImpl<>(peliculasOut, PageRequest.of(pagina, tamanoPagina), paginaPeliculas.getTotalElements());
    }

    @Override
    public PeliculaOut findByID(Long idPelicula) {
        Pelicula p = peliculas.findById(idPelicula).orElseThrow(() -> new ResourceNotFoundException("La pelicula no existe."));
        p.getActores().forEach(Object::toString);
        p.getDirectores().forEach(Object::toString);
        return PeliculaOut.fromModel(p);
    }

    @Override
    public Optional<Imagen> recuperarImagen(Long idPelicula, TamanoImagen tamanoImagen) {
        Optional<Imagen> img = (tamanoImagen.equals(TamanoImagen.LARGE))
                ? imagenes.findGrandePorIDPelicula(idPelicula)
                : imagenes.findPequenaPorIDPelicula(idPelicula);
        img.ifPresent(i -> System.out.println(i.getName()));
        return img;
    }

    @Override
    public PeliculaOut updatePelicula(Long idPelicula, PeliculaUpdate data) {
        var p = peliculas.findById(idPelicula).orElseThrow(() -> new ResourceNotFoundException("La pelicula no existe"));
        data.update(p, directores, actores);
        peliculas.save(p);
        return PeliculaOut.fromModel(p);
    }

    @Override
    public void deletePelicula(Long idPelicula) {
        peliculas.deleteById(idPelicula);
    }
}
