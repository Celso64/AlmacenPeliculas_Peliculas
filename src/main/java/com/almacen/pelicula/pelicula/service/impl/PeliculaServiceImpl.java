package com.almacen.pelicula.pelicula.service.impl;

import com.almacen.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.dto.in.PeliculaCreate;
import com.almacen.pelicula.pelicula.dto.in.PeliculaUpdate;
import com.almacen.pelicula.pelicula.dto.out.PeliculaMinOut;
import com.almacen.pelicula.pelicula.dto.out.PeliculaOut;
import com.almacen.pelicula.pelicula.entity.Imagen;
import com.almacen.pelicula.pelicula.entity.Pelicula;
import com.almacen.pelicula.pelicula.repository.ActorRepository;
import com.almacen.pelicula.pelicula.repository.DirectorRepository;
import com.almacen.pelicula.pelicula.repository.ImagenRepository;
import com.almacen.pelicula.pelicula.repository.PeliculaRepository;
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

    @Value("${page_tam.pelicula}")
    Integer tamanoPagina;

    @Override
    public PeliculaOut crearPelicula(PeliculaCreate pelicula) {
        Pelicula p = pelicula.toModel(directores, actores);
        return PeliculaOut.fromModel(peliculas.save(p));
    }

    @Override
    public Page<PeliculaMinOut> listPeliculas(Integer pagina) {

        Page<Pelicula> paginaPeliculas = peliculas.findAll(PageRequest.of(pagina, tamanoPagina));

        paginaPeliculas.getContent().forEach(p -> p.getDirectores().forEach(Object::toString));
        paginaPeliculas.getContent().forEach(p -> System.out.println("Actores (SERVICE): " + p.getActores().size()));

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
