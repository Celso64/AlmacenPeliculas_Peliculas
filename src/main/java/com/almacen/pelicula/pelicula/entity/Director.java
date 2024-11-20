package com.almacen.pelicula.pelicula.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellido;

    @ManyToMany(mappedBy = "directores")
    private Set<Pelicula> peliculas;

    public Map<String, Object> toMap() {
        return Map.of("id", id,
                "nombre", nombre,
                "apellido", apellido);
    }
}
