package com.almacen.pelicula.pelicula.dto.out;

import com.almacen.pelicula.pelicula.constant.Fecha;
import com.almacen.pelicula.pelicula.entity.Pelicula;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public record PeliculaMinOut(Long id, String titulo, String fechaSalida, String precio, List<DirectorOut> directores, List<ActorOut> actores, String imagenPequena) {

    private static final DecimalFormat formatoDecimal = new DecimalFormat("$#.##");

    public static PeliculaMinOut fromModel(Pelicula p){

        String precio = formatoDecimal.format(p.getPrecio());
        String fecha = p.getFechaSalida().format(Fecha.formato);

        var directores = p.getDirectores().stream()
                .map(DirectorOut::fromModel)
                .toList();

        System.out.println("ACTORES (DTO): " + p.getActores().size());

        var actores = p.getActores().stream()
                .map(ActorOut::fromModel)
                .toList();

        String img = (Objects.isNull(p.getImagenPequena())) ? "" : p.getImagenPequena().getFilePath();

        return new PeliculaMinOut(p.getId(), p.getTitulo(), fecha, precio, directores, actores, img);
    }

}
