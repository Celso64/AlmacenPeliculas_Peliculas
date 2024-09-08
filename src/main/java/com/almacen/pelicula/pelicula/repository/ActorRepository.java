package com.almacen.pelicula.pelicula.repository;

import com.almacen.pelicula.pelicula.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
}
