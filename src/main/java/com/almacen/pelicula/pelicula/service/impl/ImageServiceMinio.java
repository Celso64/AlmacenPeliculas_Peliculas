package com.almacen.pelicula.pelicula.service.impl;

import com.almacen.pelicula.pelicula.dto.out.ImagenOut;
import com.almacen.pelicula.pelicula.entity.Imagen;
import com.almacen.pelicula.pelicula.repository.ImagenRepository;
import com.almacen.pelicula.pelicula.service.ImagenService;
import com.almacen.pelicula.pelicula.service.TamanoImagen;
import io.minio.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

@Service
@Lazy
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ImageServiceMinio implements ImagenService {

    final MinioClient minioClient;

    final ImagenRepository imagenRepository;

    @Value("${minio.bucket.grande}")
    String bucketGrande;

    @Value("${minio.bucket.pequeno}")
    String bucketPequeno;

    @SneakyThrows
    @Override
    public void guardarImagen(MultipartFile imagen, String nombreImagen, TamanoImagen tamano) {

        Objects.requireNonNull(imagen.getOriginalFilename());
        String bucket = (tamano.equals(TamanoImagen.LARGE)) ? bucketGrande : bucketPequeno;

        crearBucketSiNoExiste(bucket);

        String nombreCompleto = String.format("%s_%s.%s", nombreImagen, tamano, imagen.getContentType());

        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(bucket)
                        .object(nombreCompleto)
                        .stream(imagen.getInputStream(), imagen.getSize(), -1)
                        .build()
        );
//
//        minioClient.putObject(PutObjectRequest
//                .builder()
//                .bucket(bucket)
//                .key(nombreImagen)
//                .build(), Paths.get(imagen.getOriginalFilename()));

        Imagen imagenNueva = new Imagen(nombreImagen, imagen.getContentType(), tamano);
        imagenRepository.save(imagenNueva);
    }

    @SneakyThrows
    @Override
    public ImagenOut buscarImagen(Imagen imagen) {
        String bucket = (imagen.getTamano().equals(TamanoImagen.LARGE)) ? bucketGrande : bucketPequeno;

        InputStream is = minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(bucket)
                        .object(imagen.getName())
                        .build()
        );

        return new ImagenOut(imagen, is.readAllBytes());
    }

//    private byte[] transfomar(ResponseInputStream<GetObjectResponse> responseInputStream) {
//        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = responseInputStream.read(buffer)) != -1) {
//                byteArrayOutputStream.write(buffer, 0, length);
//            }
//            return byteArrayOutputStream.toByteArray();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @SneakyThrows
    private void crearBucketSiNoExiste(String nombreBucket) {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(nombreBucket).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(nombreBucket).build());
        }
    }
}
