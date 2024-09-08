package com.almacen.pelicula.pelicula.constant;

import java.time.format.DateTimeFormatter;

public abstract class Fecha {
    public static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
}
