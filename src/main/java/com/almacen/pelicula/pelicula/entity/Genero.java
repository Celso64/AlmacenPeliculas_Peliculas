package com.almacen.pelicula.pelicula.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Genero {
    @OneToMany(mappedBy = "genero", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Pelicula> pelicula;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
}
