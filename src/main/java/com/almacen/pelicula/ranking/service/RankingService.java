package com.almacen.pelicula.ranking.service;

import com.almacen.pelicula.ranking.dto.in.RankingCreate;
import com.almacen.pelicula.ranking.dto.in.RankingUpdate;
import com.almacen.pelicula.ranking.dto.out.RankingOut;

import java.util.List;

public interface RankingService {

    /**
     * Crea un ranking.
     * @param datos Los datos del ranking: id usuario, id pelicula, estrellas y comentario.
     * @return El ranking creado.
     */
    RankingOut subirRanking(RankingCreate datos);

    /**
     * Lista todos los ranking.
     * @return Todos los ranking de la DB.
     */
    List<RankingOut> listarTodo();

    /**
     * Busca un ranking por su id.
     * @param id del Ranking.
     * @return el ranking, si existe.
     */
    RankingOut buscarPorID(Long id);

    /**
     * Busca todos los rankings de un usuario.
     * @param idUser ID del usuario.
     * @return Lista de todos los rankings del usuario.
     */
    List<RankingOut> buscarPorUserID(Long idUser);

    /**
     * Busca todos los rankings de una Pelicula.
     * @param idPelicula ID de la Pelicula.
     * @return Lista de todos los rankings de una pelicula.
     */
    List<RankingOut> buscarPorPeliculaID(Long idPelicula);

    /**
     * Actualiza los datos (todos o algunos) de un ranking.
     * @param id ID del ranling.
     * @param datos Datos actualizados.
     * @return El ranking actualizado.
     */
    RankingOut actualizarRanking(Long id, RankingUpdate datos);

    /**
     * Borra, fisicamente, el ranking.
     * @param id ID del ranking.
     */
    void borrarRanking(Long id);
}
