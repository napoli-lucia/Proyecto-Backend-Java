package metcamp.backend.eventsmanager.service;

import metcamp.backend.eventsmanager.entities.model.Event;
import metcamp.backend.eventsmanager.exceptions.*;
import metcamp.backend.eventsmanager.repository.EventRepository;
import metcamp.backend.eventsmanager.utils.MapperUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class EventService {

    private static final Logger logger = LogManager.getLogger();

    private final EventRepository repository;

    public EventService(MapperUtils mapperUtils, EventRepository repository) {
        this.repository = repository;
    }


    //**Funciones de los eventos**//

    public ArrayList<Event> getAllEvents() {
        return repository.getEvents();
    }

    public Event getEventById(int id){
        Optional<Event> foundEvent = repository.find(id);
        if (!foundEvent.isPresent()) {
            logger.info("El id ingresado es {}", id);
            throw new EventNotFoundException(String.format("Event %s doesn't exists", id)); //404
        }
        return foundEvent.get();
    }

    public Event createEvent(Event event) {

        if (event.getStartDateTime().isAfter(event.getEndDateTime())){
            throw new ValidationException("startDate must be before endDate");
        }

        Optional<Event> foundEvent = repository.find(event.getId());

        if (foundEvent.isPresent()) {
            logger.info("El evento con id {} ya existe", event.getId());
            throw new EventAlreadyExistsException("Event already exists"); //400
        } else {
            repository.add(event);
            return event;
        }
    }

    public void deleteEvent(int id) {
        Optional<Event> foundEvent = repository.find(id);
        logger.info("El id ingresado es {}", id);
        if (foundEvent.isPresent()) {
            repository.delete(foundEvent.get().getId());
            logger.info("El evento con id {} fue eliminado", id);
        } else{
            throw new EventNotFoundException(String.format("Event %s doesn't exists", id)); //404
        }
    }

    public Event updateEvent(int id, Event newEventData) {
        Optional<Event> foundEvent = repository.find(id);
        logger.info("El id ingresado es {}", id);
        if (foundEvent.isPresent()) {
            repository.update(id, newEventData);
            logger.info("El evento con id {} fue editado", id);
            return newEventData;
        } else {
            throw new EventNotFoundException(String.format("Event %s doesn't exists", id)); //404
        }
    }


}