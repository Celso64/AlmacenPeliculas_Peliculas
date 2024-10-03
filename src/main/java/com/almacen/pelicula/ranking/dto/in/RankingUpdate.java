package com.almacen.pelicula.ranking.dto.in;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record RankingUpdate(Optional<Long> idCliente, Optional<Long> idPelicula,

                            Optional<@Min(value = 0, message = "No se permiten estrellas negativas.")
                            @Max(value = 5, message = "No se permite mas de 5 estrellas.")
                                    Integer> estrellas,

                            Optional<@Size( max = 255,message = "Limite excedido")
                                    String> comentario) {
}
