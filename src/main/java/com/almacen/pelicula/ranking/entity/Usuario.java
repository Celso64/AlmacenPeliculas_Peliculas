package com.almacen.pelicula.ranking.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"first_name", "last_name"})})
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "id_keyloack", unique = true)
    String idKeyLoack;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(unique = true)
    String email;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Ranking> resenas;

    public Usuario(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.setResenas(new HashSet<>());
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
