package com.almacen.pelicula.config;

import io.minio.MinioClient;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class S3Config {

    @Value("${minio.endpoint}")
    String endpoint;

    @Value("${minio.user}")
    String username;

    @Value("${minio.pass}")
    String pass;

    @Bean
    public MinioClient minioClient() {
        return MinioClient
                .builder()
                .endpoint(endpoint)
                .credentials(username, pass)
                .build();
    }
}
