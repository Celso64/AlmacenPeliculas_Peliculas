package com.almacen.pelicula.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class S3Config {

    @Value("${cloud.aws.s3.endpoint}")
    String endpoint;

    @Value("${cloud.aws.credentials.access-key}")
    String username;

    @Value("${cloud.aws.credentials.secret-key}")
    String pass;

    @Value("${cloud.aws.region.static}")
    String region;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(username, pass)))
                .region(Region.of(region))
                .build();
    }
}
