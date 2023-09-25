package metcamp.backend.eventsmanager.service;


import metcamp.backend.eventsmanager.entities.model.*;
import metcamp.backend.eventsmanager.exceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ValidationService {

    public void validateCreateEvent(Event event){
        validateId(event.getId());
        validateName(event.getName());
        validateDates(event.getStartDateTime(), event.getEndDateTime());
        validateAttendeess(event.getAttendees());
        validateOrganizer(event.getOrganizer());
        validateEventType(String.valueOf(event.getEventType()));
        validatePrices(event.getPrices());
    }


    public void validateName (String name) {
        //Validar si el nombre no esta vacio y que tenga al menos 5 caracteres
        if (name == null || name.isBlank()){
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

    public void validateAttendeess(int cantidad){
        if (cantidad==0){
            throw new ValidationException("attendees must not be zero");
        } else if (cantidad < 0){
            throw new ValidationException("attendees must be positive");
        }
    }

    public void validateOrganizer (String org) {
        if (org == null || org.isBlank()){
            throw new ValidationException("organizer name is required");
        }
        if (org.length() < 3){
            throw new ValidationException("organizer name is too short");
        }
        if (org.length() > 10){
            throw new ValidationException("organizer name is too long");
        }
    }

    public void validateEventType (String eventType) {
        if (eventType.isBlank()){
            throw new ValidationException("type is required: ANIVERSARIO, CLASE_METCAMP, ENCUENTRO_METLAB");
        }
        if(!eventTypeContains(eventType)){
            throw new ValidationException("type must be one of these: ANIVERSARIO, CLASE_METCAMP, ENCUENTRO_METLAB");
        }
    }


    public void validatePrices (List<Price> prices) {
        if (prices!=null && !prices.isEmpty()){
            for(Price price : prices){
                validatePrice(price);
            }
        }
    }

    private void validatePrice(Price price){
        String type = String.valueOf(price.getType());
        String currency = String.valueOf(price.getCurrency());
        if(!ticketTypeContains(type) || type.isBlank()){
            throw new ValidationException("type must be one of these: REGULAR_FULL_PASS, REGULAR_ONE_DAY, VIP_FULL_PASS, VIP_ONE_DAY");
        }
        if(!currencyContains(currency) || currency.isBlank()){
            throw new ValidationException("currency must be one of these: ARS,CLP,COP,USD");
        }
        if(price.getValue()<=0){
            throw new ValidationException("price must be positive");
        }

    }


    //Funciones de enum
    private boolean eventTypeContains(String type) {
        for (EventType eventType : EventType.values()) {
            if (eventType.name().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    private boolean ticketTypeContains(String type) {
        for (TicketType ticketType : TicketType.values()) {
            if (ticketType.name().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    private boolean currencyContains(String c) {
        for (Currency currency : Currency.values()) {
            if (currency.name().equalsIgnoreCase(c)) {
                return true;
            }
        }
        return false;
    }




}
