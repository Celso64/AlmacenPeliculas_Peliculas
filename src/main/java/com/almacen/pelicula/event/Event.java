package com.almacen.pelicula.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Event<K, T> {

    private final Type eventType;
    private final K key;
    private final T data;

    public Event() {
        this.eventType = null;
        this.key = null;
        this.data = null;
    }

    public Event(Type eventType, K key, T data) {
        this.eventType = eventType;
        this.key = key;
        this.data = data;
    }

    @JsonIgnore
    public String getRoutingkey(String rk) {
        log.info("RK: {}{}", rk, this.getEventType());
        return rk + this.getEventType();
    }

    public enum Type {
        CREATE,
        UPDATE,
        DELETE
    }

}