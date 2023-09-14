package metcamp.backend.eventsmanager.service;


import metcamp.backend.eventsmanager.exceptions.ValidationException;
import metcamp.backend.eventsmanager.entities.model.Event;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ValidationService {

    public void validateCreateEvent(Event event){
        validateId(event.getId());
        validateName(event.getName());
        validateDates(event.getStartDateTime(), event.getEndDateTime());
        validateAttendeess(event.getAttendees());
        validateOrganizer(event.getOrganizer());
    }

    public void validateName (String name) {
        //Validar si el nombre no esta vacio y que tenga al menos 5 caracteres
        if (name.isEmpty()){
            throw new ValidationException("name is required");
        }
        if (name.length() < 5){
            throw new ValidationException("name is too short");
        }
    }

    public void validateId(int id){
        if (id==0){
            throw new ValidationException("id must not be zero");
        } else if (id < 0){
            throw new ValidationException("id must be positive");
        }
    }

    public void validateDates(LocalDateTime startDate, LocalDateTime endDate){
        //Verificar que start sea antes que end
        if (startDate.isAfter(endDate)){
            throw new ValidationException("startDate must be before endDate");
        }
    }

    public void validateAttendeess(int cantidad){
        if (cantidad==0){
            throw new ValidationException("attendees must not be zero");
        } else if (cantidad < 0){
            throw new ValidationException("attendees must be positive");
        }
    }

    public void validateOrganizer (String org) {
        if (org.isEmpty()){
            throw new ValidationException("name is required");
        }
        if (org.isBlank()){
            throw new ValidationException("name is required");
        }
        if (org.length() < 3){
            throw new ValidationException("name is too short");
        }
        if (org.length() > 10){
            throw new ValidationException("name is too long");
        }
    }

}
