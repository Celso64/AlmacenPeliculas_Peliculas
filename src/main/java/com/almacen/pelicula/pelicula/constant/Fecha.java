package com.almacen.pelicula.pelicula.constant;

import java.time.format.DateTimeFormatter;

public enum Fecha {
    FORMATO_DEFAULT(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    private DateTimeFormatter formato;

    Fecha(DateTimeFormatter formato) {
        this.formato = formato;
    }

    public DateTimeFormatter formato() {
        return this.formato;
    }
}
