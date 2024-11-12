package com.almacen.pelicula.pelicula.service.impl;

import com.almacen.pelicula.pelicula.dto.in.GeneroCreate;
import com.almacen.pelicula.pelicula.entity.Genero;
import com.almacen.pelicula.pelicula.repository.GeneroRepository;
import com.almacen.pelicula.pelicula.service.GeneroService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GeneroServiceImpl implements GeneroService {

    GeneroRepository generoRepository;

    @Override
    public Set<Genero> generos() {
        return new HashSet<>(generoRepository.findAll());
    }

    @Override
    public Genero agregarGenero(GeneroCreate genero) {
        return generoRepository.save(genero.toModel());
    }
}
