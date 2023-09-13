package metcamp.backend.eventsmanager.service;

import metcamp.backend.eventsmanager.entities.model.Event;
import metcamp.backend.eventsmanager.exceptions.ConvertionException;
import metcamp.backend.eventsmanager.exceptions.RepoException;
import metcamp.backend.eventsmanager.exceptions.ValidationException;
import metcamp.backend.eventsmanager.repository.EventRepository;
import metcamp.backend.eventsmanager.utils.MapperUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class EventService {

    private static final Logger logger = LogManager.getLogger();

    private final MapperUtils mapperUtils;
    private final EventRepository repository;
    private final ValidationService validationService;

    public EventService(MapperUtils mapperUtils, EventRepository repository, ValidationService validationService) {
        this.mapperUtils = mapperUtils;
        this.repository = repository;
        this.validationService = validationService;
    }


    //**Funciones de los eventos**//

    public ArrayList<Event> getAllEvents() {
        return repository.getEvents();

    }


    public Optional<Event> getEventById(int id){
        return repository.find(id);
    }



    public Event createEvent(String json) {
        Event event = mapperUtils.mapToEvent(json);
        validationService.validateCreateEvent(event);
        Optional<Event> foundEvent = repository.find(event.getId());

        if (foundEvent.isPresent()) {
            throw new ValidationException("Event already exists"); //400
        } else {
            repository.add(event);
            return event;
        }
    }


    public Boolean deleteEvent(int id) {
        Optional<Event> foundEvent = repository.find(id);
        Boolean result = foundEvent.isPresent();
        if (result) {
            repository.delete(foundEvent.get().getId());
        }
        return result;
    }



    public Event updateEvent(int id, String json) {
        Optional<Event> foundEvent = repository.find(id);
        if (foundEvent.isPresent()) {
            Event newEventData = mapperUtils.mapToEvent(json);
            repository.update(id, newEventData);
            //return new EventResponse(200, "Event updated", newEventData);
            return newEventData;
        } else {
            //logger.info("El id ingresado es {}", id);
            //return new Response(404, "Event Not Found");
            throw new ValidationException("Event Not Found"); //404
        }
    }







}