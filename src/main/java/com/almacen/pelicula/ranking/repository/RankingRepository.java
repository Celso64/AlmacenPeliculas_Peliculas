package com.almacen.pelicula.ranking.repository;

import com.almacen.pelicula.ranking.entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
}
