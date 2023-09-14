package metcamp.backend.eventsmanager.exceptions;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(String message) {
        super(message);
    }
}
