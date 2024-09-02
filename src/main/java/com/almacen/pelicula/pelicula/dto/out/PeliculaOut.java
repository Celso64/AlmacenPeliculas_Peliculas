package com.almacen.pelicula.pelicula.dto.out;

import com.almacen.pelicula.pelicula.entity.Director;
import com.almacen.pelicula.pelicula.entity.Pelicula;
import java.text.DecimalFormat;
import java.util.List;

public record PeliculaOut(Long id, String titulo, String sinopsis, String precio, String condicion, String genero, List<String> directores) {

    private static final DecimalFormat formatoDecimal = new DecimalFormat("$#.##");

    public static PeliculaOut fromModel(Pelicula p){

        String precio = formatoDecimal.format(p.getPrecio());
        String condicion = p.getCondicion().toString();
        String genero = p.getGenero().toString();

        List<String> directores = p.getDirectores().stream()
                        .map(Director::getNombre)
                        .toList();

        return new PeliculaOut(p.getId(), p.getTitulo(), p.getSinopsis(), precio, condicion, genero, directores);
    }
}
