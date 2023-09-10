package metcamp.backend.eventsmanager.service;

import metcamp.backend.eventsmanager.entities.model.Event;
import metcamp.backend.eventsmanager.repository.EventRepository;
import metcamp.backend.eventsmanager.utils.MapperUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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


    //Funciones de los eventos
    /*
    public Response createEvent(String json) {
        try {
            Event event = mapperUtils.mapToEvent(json);
            validationService.validateCreateEvent(event);
            Optional<Event> foundEvent = repository.find(event.getId());
            if (foundEvent.isPresent()) {
                return new Response(400, "Event already exists");
            } else {
                repository.add(event);
                return new EventResponse(201, "Event Created", event);
            }
        } catch (ConvertionException e){
            return new Response(400, "Malformed event");
        } catch (RepoException e){
            return new Response(500, e.getMessage());
        } catch (ValidationException e){
            return new Response(400, e.getMessage());
        }
    }
    */


    public ArrayList<Event> getAllEvents() {
        return repository.getEvents();

    }







    //public Response getEventById(int id){
        /*
        logger.fatal("fatal - El id ingresado es {}", id);
        logger.error("error - El id ingresado es {}", id);
        logger.warn("warn - El id ingresado es {}", id);
        logger.info("info - El id ingresado es {}", id);
        logger.debug("debug - El id ingresado es {}", id);
        logger.trace("trace - El id ingresado es {}", id);
         */
/*
        Optional<Event> foundEvent = repository.find(id);
        return foundEvent.isPresent()
                ? new EventResponse(200,"OK",foundEvent.get())
                : new Response(404, "Event Not Found");
    }


    public Response updateEvent(int id, String json) {
        try {
            Optional<Event> foundEvent = repository.find(id);
            if (foundEvent.isPresent()) {
                Event newEventData = mapperUtils.mapToEvent(json);
                repository.update(id, newEventData);
                return new EventResponse(200, "Event updated", newEventData);
            } else {
                logger.info("El id ingresado es {}", id);
                return new Response(404, "Event Not Found");
            }
        } catch (ConvertionException e){
            logger.error("El id ingresado es {} y los datos son {}", id, json);
            return new Response(400, "Malformed event");
        } catch (RepoException e){
            return new Response(500, e.getMessage());
        }
    }


    public Response deleteEvent(int id) {
        try {
            Optional<Event> foundEvent = repository.find(id);
            if (foundEvent.isPresent()) {
                repository.delete(foundEvent.get().getId());
                return new Response(204, "Event Deleted");
            } else {
                return new Response(404, "Event Not Found");
            }
        } catch (RepoException e) {
            return new Response(500, e.getMessage());
        }
    }


 */
}