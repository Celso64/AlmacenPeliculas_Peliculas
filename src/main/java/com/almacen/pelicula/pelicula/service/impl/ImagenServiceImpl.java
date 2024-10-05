package com.almacen.pelicula.pelicula.service.impl;

import com.almacen.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.dto.in.ImagenCreate;
import com.almacen.pelicula.pelicula.dto.out.ImagenOut;
import com.almacen.pelicula.pelicula.entity.Imagen;
import com.almacen.pelicula.pelicula.entity.Pelicula;
import com.almacen.pelicula.pelicula.repository.ImagenRepository;
import com.almacen.pelicula.pelicula.repository.PeliculaRepository;
import com.almacen.pelicula.pelicula.service.ImagenService;
import com.almacen.pelicula.pelicula.service.TamanoImagen;
import com.almacen.pelicula.pelicula.util.ImageUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImagenServiceImpl implements ImagenService {

    @Autowired
    PeliculaRepository peliculas;

    @Autowired
    ImagenRepository imagenRepository;

    @Autowired
    ImageUtils utils;

    @Value("${file_path_images_p}")
    String filePathPequeno;

    @Value("${file_path_images_g}")
    String filePathGrande;

    @Override
    public Boolean guardarImagenes(Long idPelicula, ImagenCreate imagenes) {

        Pelicula p = peliculas.findById(idPelicula).orElseThrow(() -> new ResourceNotFoundException("La pelicula no existe."));

        imagenes.imagenGrande().ifPresent(img -> {
            try {
                p.setImagenGrande(guardarImagen(img, filePathGrande));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        imagenes.imagenPequena().ifPresent(img -> {
            try {
                p.setImagenPequena(guardarImagen(img, filePathPequeno));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        peliculas.save(p);
        return true;
    }

    @Override
    public Imagen guardarImagen(MultipartFile imagen, String folder) throws IOException {
        String direccion = folder + imagen.getOriginalFilename();

        Imagen nuevaImagen = imagenRepository.save(
                Imagen.builder()
                        .name(imagen.getOriginalFilename())
                        .type(imagen.getContentType())
                        .filePath(direccion)
                        .build());

        File directorio = new File(folder);
        if (!directorio.exists()) directorio.mkdirs();


        Files.write(Paths.get(direccion), imagen.getBytes());
        return nuevaImagen;
    }

    @Override
    public byte[] buscarImagen(Imagen imagenData) throws IOException {
        String direccion = imagenData.getFilePath();
        return Files.readAllBytes(new File(direccion).toPath());
    }

    @Override
    public ImagenOut buscarImagen(Long idPelicula, TamanoImagen tamImagen) throws IOException {

        Imagen imagenData = (tamImagen.equals(TamanoImagen.LARGE))
                ? imagenRepository.findGrandePorIDPelicula(idPelicula)
                : imagenRepository.findPequenaPorIDPelicula(idPelicula);

        String direccion = imagenData.getFilePath();

        return new ImagenOut(imagenData, Files.readAllBytes(new File(direccion).toPath()));
    }
}
