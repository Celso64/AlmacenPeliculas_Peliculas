package com.almacen.pelicula.ranking.entity;

import com.almacen.pelicula.pelicula.entity.Pelicula;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer estrellas;

    private String comentario;

    @ManyToOne
    @JoinColumn(name = "pelicula_id")
    private Pelicula pelicula;
}
