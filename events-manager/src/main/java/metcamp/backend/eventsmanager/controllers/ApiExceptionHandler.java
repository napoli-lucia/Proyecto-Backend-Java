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
        logger.error("Not found", e);
        return ResponseEntity.status(notFound).body(new ErrorMessage(notFound, e.getMessage()));
    }

    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ErrorMessage> eventNoContentExceptionHandler(NoContentException e){
        HttpStatus noContent = HttpStatus.NO_CONTENT;
        logger.error("No content", e);
        return ResponseEntity.status(noContent).body(new ErrorMessage(noContent, e.getMessage()));
    }

    @ExceptionHandler({EventAlreadyExistsException.class,ValidationException.class,ConvertionException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> badRequestExceptionHandler(RuntimeException  e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        logger.error("Bad Request", e);
        return ResponseEntity.status(badRequest).body(new ErrorMessage(badRequest, e.getMessage()));
    }

    @ExceptionHandler(RepoException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> repoExceptionHandler(RepoException e){
        HttpStatus repoError = HttpStatus.INTERNAL_SERVER_ERROR;
        logger.error("Internal Server Error", e);
        //logger.error(String.format("Internal Server Error: %s", e.getMessage()), e);
        return ResponseEntity.status(repoError).body(new ErrorMessage(repoError, e.getMessage()));
    }



}
