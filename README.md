# Almacen de Peliculas - Micro Servicio de Peliculas
Este es el microservicio de Peliculas.
Se encarga de todo lo relativo a las peliculas:
- La gestion de las peliculas.
- La gestion de los rankings.
- La gestion de los directores.

## Funcionalidades
### 1. Peliculas
- ABMC
    - Campos:
        - id (Autogenerado)
        - titulo
        - sinopsis (Opcional, maximo 255 caracteres)
        - precio (>= 0)
        - condicion [NUEVO|USADO]
        - genero [ACCION|DRAMA|...]
        - directores (1 o mas)
        - ranking (0 o mas)
    - Consideraciones:
        - Una pelicula no debe sufrir borrado fisico de la DB. 
### 2. Rankings
- ABMC
    - Campos:
        - id(Autogenerado)
        - estrellas (de 0 a 5)
        - comentario (Opcional, maximo 255 caracteres)
    - Consideraciones:
        - Un ranking pertenece a solo 1 pelicula y 1 usuario.
        - 1 usuario solo puede emitir un ranking sobre 1 pelicula.

## Tecnologias
- Java 17
- Spring Boot
    - Spring Web
    - Spring Data JPA
    - Lombok
    - PostgresSQL Driver
    - Validation
- Maven
- Postgres
- Docker
- Postman

## Sobre el desarrollo

Fecha de Inicio del Proyecto: Domingo 1 de Septiembre 2024
