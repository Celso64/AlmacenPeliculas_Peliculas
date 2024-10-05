package com.almacen.pelicula.pelicula.service;

import com.almacen.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.dto.in.DirectorCreate;
import com.almacen.pelicula.pelicula.dto.in.DirectorUpdate;
import com.almacen.pelicula.pelicula.dto.out.DirectorOut;

import java.util.List;

public interface DirectorService {

    /**
     * Crea un Director.
     * @param director Los datos del Director: nombre(String); apellido(String).
     * @return Los datos del Director creado.
     */
    DirectorOut crearDirector(DirectorCreate director);

    /**
     * Lista todos los actores.
     * @return Lista de todos los actores.
     */
    List<DirectorOut> listar();

    /**
     * Busca un Director por su id.
     * @param idDirector ID del Director.
     * @return El Director, si existe.
     * @throws ResourceNotFoundException Si el ID no existe.
     */
    DirectorOut buscarPorID(Long idDirector) throws ResourceNotFoundException;

    /**
     * Actualiza datos de un Director.
     * @param idDirector ID del Director.
     * @param nuevosDatos Datos actualizados del Director(opcionales): nombre(String); apellido(String).
     * @return El Director con datos actualizados.
     * @throws ResourceNotFoundException Si el ID del Director no existe.
     */
    DirectorOut update(Long idDirector, DirectorUpdate nuevosDatos);

    /**
     * Borra un Director.
     * @param idDirector ID del Director.
     * @return True si lo pudo borrar; False sino.
     */
    Boolean borrarDirector(Long idDirector);
}
