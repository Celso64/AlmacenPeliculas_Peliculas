package com.almacen.pelicula.pelicula.dto.in;

import com.almacen.pelicula.pelicula.dto.util.CondicionPeliculaMapper;
import com.almacen.pelicula.pelicula.dto.validation.ValidCondicion;
import com.almacen.pelicula.pelicula.entity.Actor;
import com.almacen.pelicula.pelicula.entity.Director;
import com.almacen.pelicula.pelicula.entity.Genero;
import com.almacen.pelicula.pelicula.entity.Pelicula;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

/**
 * Representa el DTO para crear una Pelicula.
 *
 * @param titulo        Obligatorio.
 * @param sinopsis      Opcional, no puede tener mas de 255 caracteres.
 * @param precio        No puede ser negativo.
 * @param fechaSalida   Con formato dd/MM/yyyy
 * @param condicion     Nuevo o Usado.
 * @param idGenero      ID de alguno de los disponibles.
 * @param idsDirectores Al menos 1 director que exista.
 * @param idsActores    Al menos 1 actor que exista.
 * @author Carlos Farra
 * @version 2
 * @since Jueves 14 de noviembre 2024
 */
public record PeliculaCreate(@NotBlank(message = "Titulo obligatorio.") String titulo,
                             @Size(max = 255, message = "Sinopsis demasiado larga.") String sinopsis,
                             @DecimalMin(value = "0", message = "No se permite precio negativo.") Double precio,
                             @NotNull(message = "Fecha de Salida obligatoria.") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate fechaSalida,
                             @ValidCondicion String condicion,
                             @NotNull(message = "ID de Genero obligatorio.") Long idGenero,
                             @NotEmpty(message = "Una pelicula tiene al menos 1 director.") List<Long> idsDirectores,
                             @NotEmpty(message = "Una pelicula tiene al menos 1 director.") List<Long> idsActores) {


    /**
     * Convierte el DTO en un objeto Pelicula.
     *
     * @param generos    Un repositorio de Genero.
     * @param actores    Un repositorio de Actor.
     * @param directores Un repositorio de Director.
     * @return Un objeto Pelicula.
     * @author Carlos Farra
     * @since Jueves 14 de noviembre 2024
     */
    public Pelicula toModel(List<Director> directores, List<Actor> actores, Genero generos) {
        Pelicula nuevaPelicula = new Pelicula();

        nuevaPelicula.setTitulo(titulo);
        nuevaPelicula.setSinopsis(sinopsis);
        nuevaPelicula.setPrecio(precio);
        nuevaPelicula.setFechaSalida(fechaSalida);
        nuevaPelicula.setCondicion(CondicionPeliculaMapper.map(condicion));
        nuevaPelicula.setGenero(generos);
        nuevaPelicula.setDirectores(new HashSet<>(directores));
        nuevaPelicula.setActores(new HashSet<>(actores));

        return nuevaPelicula;
    }
}
