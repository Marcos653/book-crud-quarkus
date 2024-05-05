package application.exception;

import application.exception.dto.ErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;

@UtilityClass
public class GlobalExceptionHandler {

    @Provider
    public static class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

        @Override
        public Response toResponse(EntityNotFoundException exception) {
            var errorMessage = new ErrorMessage(exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessage)
                    .build();
        }
    }

    @Provider
    public static class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

        @Override
        public Response toResponse(IllegalArgumentException exception) {
            var errorMessage = new ErrorMessage(exception.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }
    }

    @Provider
    public static class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

        @Override
        public Response toResponse(ConstraintViolationException exception) {
            var errors = new ArrayList<>();
            exception.getConstraintViolations().forEach(violation -> {
                var fieldName = violation.getPropertyPath().toString();
                var message = violation.getMessage();
                errors.add(new ErrorMessage(message, fieldName));
            });

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errors)
                    .build();
        }
    }
}
