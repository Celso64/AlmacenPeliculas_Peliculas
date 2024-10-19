package com.almacen.pelicula.pelicula.dto.validation.validators;

import com.almacen.pelicula.pelicula.dto.validation.ValidImage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImagenValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    long maxSize;
    String[] allowedTypes;

    @Override
    public void initialize(ValidImage constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
        this.allowedTypes = constraintAnnotation.allowedTypes();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {

        if (value.getSize() > maxSize) return false;

        String contentType = value.getContentType();
        boolean isValidType = false;
        for (String allowedType : allowedTypes) {
            if (allowedType.equals(contentType)) {
                isValidType = true;
                break;
            }
        }

        if (!isValidType) return false;

        try {
            if (Objects.isNull(ImageIO.read(value.getInputStream()))) return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
