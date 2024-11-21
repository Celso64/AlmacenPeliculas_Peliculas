package com.almacen.pelicula.pelicula.dto.out;

import com.almacen.pelicula.pelicula.entity.Pelicula;

import java.text.DecimalFormat;
import java.util.List;

public record PeliculaOut(Long id, String titulo, String sinopsis, String precio, String condicion,
                          GeneroOut genero,
                          List<DirectorOut> directores, List<ActorOut> actores, String imagenPequena,
                          String imagenGrande) {

    private static final DecimalFormat formatoDecimal = new DecimalFormat("$#.##");

    public static PeliculaOut fromModel(Pelicula p) {

        String precio = formatoDecimal.format(p.getPrecio());
        String condicion = p.getCondicion().toString();
        GeneroOut genero = GeneroOut.fromModel(p.getGenero());

        var directores = p.getDirectores().stream()
                .map(DirectorOut::fromModel)
                .toList();

        var actores = p.getActores().stream()
                .map(ActorOut::fromModel)
                .toList();

        return new PeliculaOut(p.getId(), p.getTitulo(), p.getSinopsis(), precio, condicion, genero, directores, actores, p.getImagenPequenaName(), p.getImagenGrandeName());
    }

    public static PeliculaOut fromModel(Pelicula p, String imagenPequena, String imagenGrande) {

        String precio = formatoDecimal.format(p.getPrecio());
        String condicion = p.getCondicion().toString();
        GeneroOut genero = GeneroOut.fromModel(p.getGenero());

        var directores = p.getDirectores().stream()
                .map(DirectorOut::fromModel)
                .toList();

        var actores = p.getActores().stream()
                .map(ActorOut::fromModel)
                .toList();

        return new PeliculaOut(p.getId(), p.getTitulo(), p.getSinopsis(), precio, condicion, genero, directores, actores, imagenPequena, imagenGrande);
    }
}
