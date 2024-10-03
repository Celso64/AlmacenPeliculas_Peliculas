package com.almacen.pelicula.pelicula.service.impl;

import com.almacen.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.dto.in.DirectorCreate;
import com.almacen.pelicula.pelicula.dto.in.DirectorUpdate;
import com.almacen.pelicula.pelicula.dto.out.DirectorOut;
import com.almacen.pelicula.pelicula.entity.Director;
import com.almacen.pelicula.pelicula.repository.DirectorRepository;
import com.almacen.pelicula.pelicula.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorServiceImpl implements DirectorService {

    @Autowired
    private DirectorRepository directores;

    @Override
    public DirectorOut crearDirector(DirectorCreate director) {
        Director nuevoDirector = director.toModel();
        return DirectorOut.fromModel(directores.save(nuevoDirector));
    }

    @Override
    public List<DirectorOut> listar() {
        return directores.findAll().stream().map(DirectorOut::fromModel).toList();
    }

    @Override
    public DirectorOut buscarPorID(Long idDirector) throws ResourceNotFoundException {
        Director directorExistente = directores.findById(idDirector)
                .orElseThrow(() -> new ResourceNotFoundException("Director no encontrado"));

        return DirectorOut.fromModel(directorExistente);
    }

    @Override
    public DirectorOut update(Long idDirector, DirectorUpdate nuevosDatos) {

        Director directorExistente = directores.findById(idDirector)
                .orElseThrow(() -> new ResourceNotFoundException("Director no encontrado"));

        Director directorActualizado = nuevosDatos.toModel(directorExistente);
        return DirectorOut.fromModel(directores.save(directorActualizado));

    }

    @Override
    public Boolean borrarDirector(Long idDirector) {
        if (directores.existsById(idDirector)) {
            directores.deleteById(idDirector);
            return true;
        }
        return false;
    }
}
