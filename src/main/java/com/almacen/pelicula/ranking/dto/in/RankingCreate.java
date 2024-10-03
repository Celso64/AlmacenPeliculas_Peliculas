package com.almacen.pelicula.ranking.dto.in;

import com.almacen.pelicula.pelicula.entity.Pelicula;
import com.almacen.pelicula.ranking.entity.Ranking;
import com.almacen.pelicula.ranking.entity.Usuario;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record RankingCreate(Long idCliente, Long idPelicula,

                            @Min(value = 0, message = "No se permiten estrellas negativas.")
                            @Max(value = 5, message = "No se permite mas de 5 estrellas.")
                            Integer estrellas,

                            @Size( max = 255,message = "Limite excedido")
                            String comentario) {

    public Ranking toModel(Usuario usuario, Pelicula pelicula){

        Ranking nuevo = new Ranking();

        nuevo.setEstrellas(estrellas);
        nuevo.setComentario(comentario);
        nuevo.setUsuario(usuario);
        nuevo.setPelicula(pelicula);

        return nuevo;
    }
}
