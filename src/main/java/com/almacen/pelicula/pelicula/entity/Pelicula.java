package com.almacen.pelicula.pelicula.entity;

import com.almacen.pelicula.ranking.entity.Ranking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
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

    @Column(name = "fecha_salida")
    private LocalDate fechaSalida;

    @Enumerated(EnumType.ORDINAL)
    private CondicionPelicula condicion;

    @Enumerated(EnumType.STRING)
    private GeneroPelicula genero;


    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "imagen_pequena_id", referencedColumnName = "id")
    private Imagen imagenPequena;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "imagen_grande_id", referencedColumnName = "id")
    private Imagen imagenGrande;


    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "pelicula_director",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private Set<Director> directores;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "pelicula_actor",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actores;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ranking> rankings;

}
