package com.almacen.pelicula.pelicula.service.impl;

import com.almacen.pelicula.pelicula.dto.out.ImagenOut;
import com.almacen.pelicula.pelicula.entity.Imagen;
import com.almacen.pelicula.pelicula.repository.ImagenRepository;
import com.almacen.pelicula.pelicula.service.ImagenService;
import com.almacen.pelicula.pelicula.service.TamanoImagen;
import io.minio.*;
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
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
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
        String bucket = getBucket(tamano);

        crearBucketSiNoExiste(bucket);

        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(bucket)
                        .object(nombreImagen)
                        .contentType(imagen.getContentType())
                        .stream(imagen.getInputStream(), imagen.getSize(), -1)
                        .build()
        );

        Imagen imagenNueva = new Imagen(nombreImagen, imagen.getContentType(), tamano);
        imagenRepository.save(imagenNueva);
    }

    @Override
    public ImagenOut buscarImagen(Imagen imagen) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.warn("STEP 0 - Entro en buscarImagen()");
        String bucket = getBucket(imagen.getTamano());
        log.warn("STEP 1 - Antes de MINIO");
        InputStream is = minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(bucket)
                        .object(imagen.getName())
                        .build()
        );
        log.warn("STEP 2 - After MINIO");
        log.info("Imagen {} - Data {}", imagen.getName(), (Objects.isNull(is) ? "VACIO" : "EXISTE"));
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

    private void crearBucketSiNoExiste(String nombreBucket) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(nombreBucket).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(nombreBucket).build());
        }
    }

    private String getBucket(TamanoImagen tamanoImagen) {
        return (tamanoImagen.equals(TamanoImagen.LARGE)) ? bucketGrande : bucketPequeno;
    }
}
