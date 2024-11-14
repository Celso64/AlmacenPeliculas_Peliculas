package com.almacen.pelicula.pelicula.dto.validation;

import com.almacen.pelicula.pelicula.dto.validation.validators.CondicionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CondicionValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCondicion {
    String message() default "La condición solo puede ser 'nuevo' o 'usado'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
