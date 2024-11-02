package com.almacen.pelicula.event;

public interface Listener<K, T> {

    void escuchar(Event<K, T> evento);
}
