package com.almacen.pelicula.pelicula.dto.in;

import com.almacen.pelicula.pelicula.dto.util.CondicionPeliculaMapper;
import com.almacen.pelicula.pelicula.dto.util.GeneroPeliculaMapper;
import com.almacen.pelicula.pelicula.entity.Actor;
import com.almacen.pelicula.pelicula.entity.Director;
import com.almacen.pelicula.pelicula.entity.Pelicula;
import com.almacen.pelicula.pelicula.repository.ActorRepository;
import com.almacen.pelicula.pelicula.repository.DirectorRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Representa el DTO para crear una Pelicula.
 *
 * @param titulo        Obligatorio.
 * @param sinopsis      Opcional, no puede tener mas de 255 caracteres.
 * @param precio        No puede ser negativo.
 * @param fechaSalida   Con formato dd-MM-yyyy
 * @param condicion     Nuevo o Usado.
 * @param genero        Alguno de los disponibles.
 * @param idsDirectores Al menos 1 director que exista.
 * @param idsActores    Al menos 1 actor que exista.
 * @author Carlos Farra
 * @since Domingo 1 de Septiembre 2024
 */
public record PeliculaCreate(@NotBlank(message = "Titulo obligatorio.") String titulo,
                             @Size(max = 255, message = "Sinopsis demasiado larga.") String sinopsis,
                             @DecimalMin(value = "0", message = "No se permite precio negativo.") Double precio,
                             @NotNull(message = "Fecha de Salida obligatoria.") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") LocalDate fechaSalida,
                             String condicion, String genero,
                             @NotEmpty(message = "Una pelicula tiene al menos 1 director.") List<Long> idsDirectores,
                             @NotEmpty(message = "Una pelicula tiene al menos 1 director.") List<Long> idsActores
) {


    /**
     * Convierte el DTO en un objeto Pelicula.
     *
     * @param directores Un repositorio de Director.
     * @return Un objeto Pelicula.
     * @author Carlos Farra
     * @since Domingo 1 de Septiembre 2024
     */
    public Pelicula toModel(DirectorRepository directores, ActorRepository actores) {
        System.out.println("HOLA");
        System.out.println(idsDirectores);
        var ds = directores.findAllById(idsDirectores);
        System.out.println("P1");
        Set<Director> d = new HashSet<>(ds);
        System.out.println("P2");
        System.out.println(d);
        Set<Actor> a = new HashSet<>(actores.findAllById(idsActores));
        System.out.println(a);

        Pelicula nuevaPelicula = new Pelicula();

        nuevaPelicula.setTitulo(titulo);
        nuevaPelicula.setSinopsis(sinopsis);
        nuevaPelicula.setPrecio(precio);
        nuevaPelicula.setFechaSalida(fechaSalida);
        nuevaPelicula.setCondicion(CondicionPeliculaMapper.map(condicion));
        nuevaPelicula.setGenero(GeneroPeliculaMapper.map(genero));
        nuevaPelicula.setDirectores(d);
        nuevaPelicula.setActores(a);

        return nuevaPelicula;
    }
}
