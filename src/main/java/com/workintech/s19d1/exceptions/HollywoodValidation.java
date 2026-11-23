package com.workintech.s19d1.exceptions;
import org.springframework.http.HttpStatus;

public final class HollywoodValidation {

    private HollywoodValidation() {
    }

    public static void validateId(Long id) {

        if (id == null || id <= 0) {

            throw new ApiException(
                    HttpStatus.BAD_REQUEST,
                    "Id must be greater than zero"
            );
        }
    }

    public static void validateRating(Double rating) {

        if (rating == null
                || !Double.isFinite(rating)
                || rating < 0
                || rating > 10) {

            throw new ApiException(
                    HttpStatus.BAD_REQUEST,
                    "Rating must be between 0 and 10"
            );
        }
    }

    public static void validateNotNull(
            Object object,
            String message) {

        if (object == null) {
            throw new ApiException(
                    HttpStatus.BAD_REQUEST,
                    message
            );
        }
    }
}
