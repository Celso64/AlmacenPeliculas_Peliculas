package com.almacen.pelicula.ranking.repository;

import com.almacen.pelicula.ranking.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findOneByIdKeyLoack(String idKeyLoack);

    boolean existsByIdKeyLoack(String idKeyLoack);

}
