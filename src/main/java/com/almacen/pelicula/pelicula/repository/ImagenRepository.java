package com.almacen.pelicula.pelicula.repository;

import com.almacen.pelicula.pelicula.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {

    @Query("SELECT i FROM Imagen i WHERE i.id = (SELECT p.imagenPequena.id FROM Pelicula p WHERE p.id = :idPelicula)")
    Optional<Imagen> findPequenaPorIDPelicula(@Param("idPelicula") Long idPelicula);

    @Query("SELECT i FROM Imagen i WHERE i.id = (SELECT p.imagenGrande.id FROM Pelicula p WHERE p.id = :idPelicula)")
    Optional<Imagen> findGrandePorIDPelicula(@Param("idPelicula") Long idPelicula);


}
