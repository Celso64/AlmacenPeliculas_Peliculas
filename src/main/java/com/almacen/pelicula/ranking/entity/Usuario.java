package com.almacen.pelicula.ranking.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "id_usuario",unique = true)
    Long idUser;

    @Column(unique = true)
    String username;

    @ManyToOne
    Set<Ranking> resenas;

}
