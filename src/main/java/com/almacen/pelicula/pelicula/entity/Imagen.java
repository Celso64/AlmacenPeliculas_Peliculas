package com.almacen.pelicula.pelicula.entity;

import com.almacen.pelicula.pelicula.service.TamanoImagen;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "nombre", unique = true, length = 255)
    String name;

    String type;

    @Enumerated(EnumType.ORDINAL)
    TamanoImagen tamano;

    public Imagen(String name, String type, TamanoImagen tamano) {
        this.name = name;
        this.type = type;
        this.tamano = tamano;
    }
}
