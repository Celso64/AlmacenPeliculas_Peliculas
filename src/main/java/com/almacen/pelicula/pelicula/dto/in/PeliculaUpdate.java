package com.almacen.pelicula.pelicula.dto.in;

import com.almacen.pelicula.pelicula.entity.Pelicula;
import com.almacen.pelicula.pelicula.repository.ActorRepository;
import com.almacen.pelicula.pelicula.repository.DirectorRepository;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public record PeliculaUpdate(Optional<@NotBlank(message = "Titulo obligatorio.") String> titulo,
                             Optional<@Size(max = 255, message = "Sinopsis demasiado larga.") String> sinopsis,
                             Optional<@DecimalMin(value = "0", message = "No se permite precio negativo.") Double> precio,
                             Optional<@NotNull(message = "Fecha de Salida obligatoria.") LocalDate> fechaSalida,
                             Optional<String> condicion, Optional<String> genero,
                             Optional<@NotEmpty(message = "Una pelicula tiene al menos 1 director.") List<Long>> idsDirectores,
                             Optional<@NotEmpty(message = "Una pelicula tiene al menos 1 director.") List<Long>> idsActores) {

    public void update(Pelicula p, DirectorRepository directores, ActorRepository actores) {
        titulo.ifPresent(p::setTitulo);
        sinopsis.ifPresent(p::setSinopsis);
        precio.ifPresent(p::setPrecio);

        if (idsDirectores.isPresent()) {
            var directoresNuevos = directores.findAllById(idsDirectores.get());
            p.setDirectores(new HashSet<>(directoresNuevos));
        }

        if (idsActores.isPresent()) {
            var actoresNuevos = actores.findAllById(idsActores.get());
            p.setActores(new HashSet<>(actoresNuevos));
        }

    }
}
