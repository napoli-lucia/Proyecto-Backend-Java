package metcamp.backend.eventsmanager.service;


import metcamp.backend.eventsmanager.entities.model.*;
import metcamp.backend.eventsmanager.exceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ValidationService {

    public void validateCreateEvent(Event event){
        validateDates(event.getStartDateTime(), event.getEndDateTime());
    }


    public void validateDates(LocalDateTime startDate, LocalDateTime endDate){
        if (startDate == null){
            throw new ValidationException("startDate is required");
        }
        if (endDate == null){
            throw new ValidationException("endDate is required");
        }
        //Verificar que start sea antes que end
        if (startDate.isAfter(endDate)){
            throw new ValidationException("startDate must be before endDate");
        }
    }

}
