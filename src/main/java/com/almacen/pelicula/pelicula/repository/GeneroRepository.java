package com.almacen.pelicula.pelicula.repository;

import com.almacen.pelicula.pelicula.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
}
