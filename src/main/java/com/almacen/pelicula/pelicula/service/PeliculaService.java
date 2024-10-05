package com.almacen.pelicula.pelicula.service;

import com.almacen.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.dto.in.PeliculaCreate;
import com.almacen.pelicula.pelicula.dto.out.PeliculaMinOut;
import com.almacen.pelicula.pelicula.dto.out.PeliculaOut;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface PeliculaService {

    /**
     * Crea una Pelicula.
     *
     * @param pelicula Los datos de la Pelicula.
     * @return La Pelicula creada.
     * @throws IOException Si hay error al almacenar la imagen.
     */
    PeliculaOut crearPelicula(PeliculaCreate pelicula) throws IOException;

    /**
     * Lista las peliculas,por Pagina.
     *
     * @param pagina Numero de la pagina solicitada, por default 0.
     * @return La pagina con las peliculas.
     */
    Page<PeliculaMinOut> listPeliculas(Integer pagina);

    /**
     * Busca una pelicula por ID.
     *
     * @param idPelicula ID de la Pelicula.
     * @return La pelicula, si existe.
     * @throws ResourceNotFoundException Si la Pelicula no existe.
     */
    PeliculaOut findByID(Long idPelicula);
}
