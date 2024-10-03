package com.almacen.pelicula.ranking.repository;

import com.almacen.pelicula.ranking.entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {

    @Query(value = "SELECT r FROM Ranking r WHERE r.usuario.id = ?1")
    List<Ranking> findByUsuarioId(Long idUsuario);

    @Query(value = "SELECT r FROM Ranking r WHERE r.pelicula.id = ?1")
    List<Ranking> findByPeliculaId(Long idUsuario);
}
