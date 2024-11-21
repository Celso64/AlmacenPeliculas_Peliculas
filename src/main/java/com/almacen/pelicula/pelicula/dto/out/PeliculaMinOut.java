package com.almacen.pelicula.pelicula.dto.out;

import com.almacen.pelicula.pelicula.constant.Fecha;
import com.almacen.pelicula.pelicula.entity.Pelicula;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public record PeliculaMinOut(Long id, String titulo, String sinopsis, String fechaSalida, String precio,
                             String condicion, GeneroOut genero, List<DirectorOut> directores,
                             List<ActorOut> actores, String imagenPequena) {

    private static final DecimalFormat formatoDecimal = new DecimalFormat("$#.##");

    public static PeliculaMinOut fromModel(Pelicula p) {

        String precio = formatoDecimal.format(p.getPrecio());
        String fecha = p.getFechaSalida().format(Fecha.FORMATO_DEFAULT.formato());

        var directores = p.getDirectores().stream()
                .map(DirectorOut::fromModel)
                .toList();

        var actores = p.getActores().stream()
                .map(ActorOut::fromModel)
                .toList();

        String img = (Objects.isNull(p.getImagenPequena())) ? "" : p.getImagenPequena().getName();

        GeneroOut genero = GeneroOut.fromModel(p.getGenero());

        return new PeliculaMinOut(p.getId(), p.getTitulo(), p.getSinopsis(), fecha, precio, p.getCondicion().toString(), genero, directores, actores, img);
    }

}
