package com.almacen.pelicula.pelicula.entity;

import com.almacen.pelicula.ranking.entity.Ranking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo, sinopsis;

    private Double precio;

    @Enumerated(EnumType.ORDINAL)
    private CondicionPelicula condicion;

    @Enumerated(EnumType.STRING)
    private GeneroPelicula genero;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "pelicula_director", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "pelicula_id"), // Clave foránea para Pelicula
            inverseJoinColumns = @JoinColumn(name = "director_id") // Clave foránea para Director
    )
    private Set<Director> directores;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ranking> rankings;

}
