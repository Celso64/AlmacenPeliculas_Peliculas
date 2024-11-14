package com.almacen.pelicula.pelicula.dto.validation.validators;

import com.almacen.pelicula.pelicula.dto.validation.ValidCondicion;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CondicionValidator implements ConstraintValidator<ValidCondicion, String> {

    @Override
    public boolean isValid(String condicion, ConstraintValidatorContext context) {
        if (condicion == null) {
            return true; // Se permite nulo, usa @NotBlank si quieres requerir que no sea nulo
        }
        return condicion.equalsIgnoreCase("nuevo") || condicion.equalsIgnoreCase("usado");
    }
}
