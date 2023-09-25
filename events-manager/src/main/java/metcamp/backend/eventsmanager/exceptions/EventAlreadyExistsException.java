package metcamp.backend.eventsmanager.exceptions;

public class EventAlreadyExistsException extends RuntimeException{
    public EventAlreadyExistsException(String message) {
        super(message);
    }
}
