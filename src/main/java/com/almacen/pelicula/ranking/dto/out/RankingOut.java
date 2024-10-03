package com.almacen.pelicula.ranking.dto.out;

import com.almacen.pelicula.ranking.entity.Ranking;

public record RankingOut(Long id, String username, String tituloPelicula, Integer estrellas, String comentario) {

    public static RankingOut fromModel(Ranking r){
        return new RankingOut(r.getId(), r.getUsername(), r.getTituloPelicula(), r.getEstrellas(), r.getComentario());
    }
}
