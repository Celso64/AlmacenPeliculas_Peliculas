package com.almacen.pelicula.pelicula.service;

import com.almacen.pelicula.pelicula.dto.in.DirectorCreate;
import com.almacen.pelicula.pelicula.dto.in.DirectorUpdate;
import com.almacen.pelicula.pelicula.dto.out.DirectorOut;
import com.almacen.pelicula.pelicula.entity.Director;
import com.almacen.pelicula.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService {

    @Autowired
    private DirectorRepository directores;

    public DirectorOut crearDirector(DirectorCreate director){
        Director d = director.toModel();
        return DirectorOut.fromModel(directores.save(d));
    }

    public List<DirectorOut> listar(){
        return directores.findAll().stream().map(DirectorOut::fromModel).toList();
    }

    public DirectorOut buscarPorID(Long idDirector) throws ResourceNotFoundException {
        Director directorExistente = directores.findById(idDirector)
                .orElseThrow(() -> new ResourceNotFoundException("Director no encontrado"));

        return DirectorOut.fromModel(directorExistente);
    }

    public DirectorOut update(Long idDirector, DirectorUpdate nuevosDatos) throws ResourceNotFoundException {

        Director directorExistente = directores.findById(idDirector)
                .orElseThrow(() -> new ResourceNotFoundException("Director no encontrado"));

        Director directorActualizado = nuevosDatos.toModel(directorExistente);
        return DirectorOut.fromModel(directores.save(directorActualizado));

    }

    public Boolean borrarDirector(Long idDirector){
        if(directores.existsById(idDirector)){
            directores.deleteById(idDirector);
            return true;
        }
        return false;
    }
}
