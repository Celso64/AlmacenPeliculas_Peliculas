package com.almacen.pelicula.pelicula.service.impl;

import com.almacen.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.entity.Imagen;
import com.almacen.pelicula.pelicula.entity.Pelicula;
import com.almacen.pelicula.pelicula.repository.ImagenRepository;
import com.almacen.pelicula.pelicula.repository.PeliculaRepository;
import com.almacen.pelicula.pelicula.service.ImagenService;
import com.almacen.pelicula.pelicula.service.TamanoImagen;
import com.almacen.pelicula.pelicula.util.ImageUtils;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class ImageServiceMinio implements ImagenService {

    final ImageUtils imageUtils;

    final MinioClient minioClient;

    final ImagenRepository imagenRepository;

    final PeliculaRepository peliculaRepository;

    @Value("${minio.bucket.grande}")
    String bucketGrande;

    @Value("${minio.bucket.pequeno}")
    String bucketPequeno;

    @SneakyThrows
    @Override
    public void guardarImagen(MultipartFile imagen, Long idPelicula, TamanoImagen tamano) {

        Pelicula pelicula = peliculaRepository.findById(idPelicula).orElseThrow(() -> new ResourceNotFoundException("La pelicula no existe."));
        String nombre = imageUtils.generarNombre(idPelicula, tamano);

        Objects.requireNonNull(imagen.getOriginalFilename());
        String bucket = getBucket(tamano);

        crearBucketSiNoExiste(bucket);

        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(bucket)
                        .object(nombre)
                        .contentType(imagen.getContentType())
                        .stream(imagen.getInputStream(), imagen.getSize(), -1)
                        .build()
        );

        Imagen imagenNueva = new Imagen(nombre, imagen.getContentType(), tamano);
        Imagen imagenSave = imagenRepository.save(imagenNueva);

        if (tamano.equals(TamanoImagen.LARGE)) {
            pelicula.setImagenGrande(imagenSave);
        } else {
            pelicula.setImagenPequena(imagenSave);
        }
        peliculaRepository.save(pelicula);
    }
// Esto no se va a usar porque probaremos usar querys directas sobre MINIO
//    @Override
//    public ImagenOut buscarImagen(Imagen imagen) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        log.warn("STEP 0 - Entro en buscarImagen()");
//        String bucket = getBucket(imagen.getTamano());
//        log.warn("STEP 1 - Antes de MINIO");
//        InputStream is = minioClient.getObject(
//                GetObjectArgs
//                        .builder()
//                        .bucket(bucket)
//                        .object(imagen.getName())
//                        .build()
//        );
//        log.warn("STEP 2 - After MINIO");
//        log.info("Imagen {} - Data {}", imagen.getName(), (Objects.isNull(is) ? "VACIO" : "EXISTE"));
//        return new ImagenOut(imagen, is.readAllBytes());
//    }

    private void crearBucketSiNoExiste(String nombreBucket) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(nombreBucket).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(nombreBucket).build());
        }
    }

    private String getBucket(TamanoImagen tamanoImagen) {
        return (tamanoImagen.equals(TamanoImagen.LARGE)) ? bucketGrande : bucketPequeno;
    }
}
