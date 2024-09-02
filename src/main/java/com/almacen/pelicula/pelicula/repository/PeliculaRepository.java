package com.almacen.pelicula.pelicula.repository;

import com.almacen.pelicula.pelicula.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
}
