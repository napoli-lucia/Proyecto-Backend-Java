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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> eventNotFoundExceptionHandler(EventNotFoundException e){

        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ErrorMessage message = new ErrorMessage(notFound, e.getMessage());
        return ResponseEntity.status(notFound).body(message);
    }

    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ErrorMessage> eventNoContentExceptionHandler(NoContentException e){

        HttpStatus noContent = HttpStatus.NO_CONTENT;
        ErrorMessage message = new ErrorMessage(noContent, e.getMessage());
        return ResponseEntity.status(noContent).body(message);
    }

    @ExceptionHandler({EventAlreadyExistsException.class,ValidationException.class,ConvertionException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> badRequestExceptionHandler(RuntimeException  e){

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorMessage message = new ErrorMessage(badRequest, e.getMessage());
        return ResponseEntity.status(badRequest).body(message);
    }

    @ExceptionHandler(RepoException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> repoExceptionHandler(RepoException e){

        HttpStatus repoError = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorMessage message = new ErrorMessage(repoError, e.getMessage());
        return ResponseEntity.status(repoError).body(message);
    }



}