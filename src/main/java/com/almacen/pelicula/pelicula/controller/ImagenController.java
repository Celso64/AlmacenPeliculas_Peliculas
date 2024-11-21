package com.almacen.pelicula.pelicula.controller;

import com.almacen.pelicula.pelicula.dto.in.ImagenCreate;
import com.almacen.pelicula.pelicula.service.ImagenService;
import com.almacen.pelicula.pelicula.service.TamanoImagen;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pelicula/imagen")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Validated
@Slf4j
public class ImagenController {

    ImagenService imagenes;
//    PeliculaService peliculas;
//
//    @GetMapping("/{idPelicula}")
//    public ResponseEntity<byte[]> getImagen(@PathVariable("idPelicula") Long idPelicula, @RequestParam(required = true, defaultValue = "p") String tamano) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        log.info("GET imagen de Pelicula {} tamano {}", idPelicula, tamano);
//
//        TamanoImagen tamanoImagen = (tamano.equalsIgnoreCase("p")) ? TamanoImagen.SMALL : TamanoImagen.LARGE;
//        Imagen imagen = peliculas.recuperarImagen(idPelicula, tamanoImagen).orElseThrow(() -> new ResourceNotFoundException("No existe la imagen"));
//        var res = imagenes.buscarImagen(imagen);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType(res.imagen().getType()));
//
//        return new ResponseEntity<>(res.contenido(), headers, HttpStatus.OK);
//    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> agregarImagenes(@PathVariable("id") Long idPelicula, @Valid ImagenCreate imagen) {

        imagen.imagenPequena().ifPresent(pequena -> imagenes.guardarImagen(pequena, idPelicula, TamanoImagen.SMALL));
        imagen.imagenGrande().ifPresent(grande -> imagenes.guardarImagen(grande, idPelicula, TamanoImagen.LARGE));

        return ResponseEntity.ok().build();
    }
}
