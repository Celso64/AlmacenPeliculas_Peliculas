package com.almacen.pelicula.event;

import com.almacen.pelicula.ranking.dto.in.rabbitmq.UsuarioDelete;

public interface Listener<K, T> {

    void create(Event<K, T> evento);

    void delete(Event<String, UsuarioDelete> evento);
}
