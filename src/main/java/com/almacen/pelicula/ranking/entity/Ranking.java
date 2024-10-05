package com.almacen.pelicula.ranking.entity;

import com.almacen.pelicula.pelicula.entity.Pelicula;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer estrellas;

    @Column(length = 255)
    String comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "pelicula_id")
    Pelicula pelicula;

    public String getUsername() {
        return this.usuario.getUsername();
    }

    public String getTituloPelicula() {
        return this.pelicula.getTitulo();
    }
}
