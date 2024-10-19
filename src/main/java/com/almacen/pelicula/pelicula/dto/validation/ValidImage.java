package com.almacen.pelicula.pelicula.dto.validation;

import com.almacen.pelicula.pelicula.dto.validation.validators.ImagenValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImagenValidator.class)
public @interface ValidImage {

    String message() default "Invalid image file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // Parámetros adicionales para la validación
    long maxSize() default 5 * 1024 * 1024; // 5 MB

    String[] allowedTypes() default {"image/jpeg", "image/png", "image/gif"};
}
