package com.almacen.pelicula.pelicula.service;

import com.almacen.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.dto.in.PeliculaCreate;
import com.almacen.pelicula.pelicula.dto.out.PeliculaMinOut;
import com.almacen.pelicula.pelicula.dto.out.PeliculaOut;
import com.almacen.pelicula.pelicula.entity.Pelicula;
import com.almacen.pelicula.pelicula.repository.ActorRepository;
import com.almacen.pelicula.pelicula.repository.DirectorRepository;
import com.almacen.pelicula.pelicula.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculas;

    @Autowired
    private DirectorRepository directores;

    @Autowired
    private ActorRepository actores;

    @Autowired
    private ImagenService imagenes;

    @Value("file_path_images_p")
    private String filePathPequeno;

    @Value("file_path_images_g")
    private String filePathGrande;

    @Value("${tam_page_pelicula}")
    private Integer tamanoPagina;

    public PeliculaOut crearPelicula(PeliculaCreate pelicula) throws IOException {
        Pelicula p = pelicula.toModel(directores, actores);
        return PeliculaOut.fromModel(peliculas.save(p));
    }

    public Page<PeliculaMinOut> listPeliculas(Integer pagina) {

        Page<Pelicula> paginaPeliculas = peliculas.findAll(PageRequest.of(pagina, tamanoPagina));

        paginaPeliculas.getContent().forEach(p -> p.getDirectores().forEach(Object::toString));
        paginaPeliculas.getContent().forEach(p -> System.out.println("Actores (SERVICE): " + p.getActores().size()));

        List<PeliculaMinOut> peliculasOut = paginaPeliculas
                .getContent()
                .stream()
                .map(PeliculaMinOut::fromModel)
                .toList();

        return new PageImpl<>(peliculasOut, PageRequest.of(pagina, tamanoPagina), paginaPeliculas.getTotalElements());
    }

    public PeliculaOut findByID(Long idPelicula) {
        Pelicula p = peliculas.findById(idPelicula).orElseThrow(() -> new ResourceNotFoundException("La pelicula no existe."));
        p.getActores().forEach(Object::toString);
        return PeliculaOut.fromModel(p);
    }

}
