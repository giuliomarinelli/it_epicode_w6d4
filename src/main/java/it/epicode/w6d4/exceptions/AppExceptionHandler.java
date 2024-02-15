package it.epicode.w6d4.exceptions;

import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorModel> badRequest(BadRequestException e) {
        return new ResponseEntity<ErrorModel>(new ErrorModel(HttpStatus.BAD_REQUEST.value(), "Bad request",
                e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintReferenceError.class)
    public ResponseEntity<ErrorModel> constraintReferenceError(ConstraintReferenceError e) {
        return badRequest(e);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorModel> notFound(NotFoundException e) {
        return new ResponseEntity<ErrorModel>(new ErrorModel(HttpStatus.NOT_FOUND.value(), "Not found",
                e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaginationSearchException.class)
    public ResponseEntity<ErrorModel> paginationSearchError(PaginationSearchException e) {
        return badRequest(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModel> internalServerError(Exception e) {
        return new ResponseEntity<ErrorModel>(new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error",
                e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
