package com.almacen.pelicula.pelicula.entity;

import com.almacen.pelicula.pelicula.amqp.PeliculaPublish;
import com.almacen.pelicula.ranking.entity.Ranking;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(PeliculaPublish.class)
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titulo, sinopsis;

    Double precio;

    @Column(name = "fecha_salida")
    LocalDate fechaSalida;

    @Enumerated(EnumType.ORDINAL)
    CondicionPelicula condicion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_genero")
    Genero genero;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagen_pequena_id", referencedColumnName = "id")
    Imagen imagenPequena;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagen_grande_id", referencedColumnName = "id")
    Imagen imagenGrande;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "pelicula_director",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    Set<Director> directores;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "pelicula_actor",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    Set<Actor> actores;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Ranking> rankings;

    public String getImagenPequenaName() {
        return Objects.isNull(imagenPequena) ? null : imagenPequena.getName();
    }

    public String getImagenGrandeName() {
        return Objects.isNull(imagenGrande) ? null : imagenGrande.getName();
    }

}
