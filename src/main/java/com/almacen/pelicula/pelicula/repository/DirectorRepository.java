package com.almacen.pelicula.pelicula.repository;

import com.almacen.pelicula.pelicula.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
}
