package metcamp.backend.eventsmanager.controllers;

import metcamp.backend.eventsmanager.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public ResponseEntity<ErrorMessage> eventNotFoundExceptionHandler(EventNotFoundException e){
        logger.error(String.format("Not found Error: %s", e.getMessage()), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler({EventAlreadyExistsException.class,ValidationException.class,ConvertionException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public ResponseEntity<ErrorMessage> badRequestExceptionHandler(RuntimeException  e){
        logger.error(String.format("Bad Request Error: %s", e.getMessage()), e);
        return ResponseEntity.badRequest().body(new ErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler({RepoException.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500
    public ResponseEntity<ErrorMessage> repoExceptionHandler(Exception e){
        logger.error(String.format("Internal Server Error: %s", e.getMessage()), e);
        return ResponseEntity.internalServerError().body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }



}
