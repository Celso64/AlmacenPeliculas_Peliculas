package com.almacen.pelicula.pelicula.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genero {
    @JsonIgnore
    @OneToMany(mappedBy = "genero", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    Set<Pelicula> pelicula;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
}
