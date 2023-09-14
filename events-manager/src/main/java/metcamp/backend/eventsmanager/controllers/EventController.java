package metcamp.backend.eventsmanager.controllers;

import metcamp.backend.eventsmanager.entities.model.Event;
import metcamp.backend.eventsmanager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/metcamp/backend/events")

public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllEvents(){
        ArrayList<Event> temporalList = eventService.getAllEvents();
        return ResponseEntity.ok(Map.of("events",temporalList)); //200
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Map<String,Object>> getEventById(@PathVariable int id){
        Event foundEvent = eventService.getEventById(id);
        return ResponseEntity.ok(Map.of("event",foundEvent)); //200
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createEvent(@RequestBody String body){
        Event event = eventService.createEvent(body);
        return ResponseEntity.status(201).body(Map.of("Event Created",event)); //201
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable int id){
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Map<String,Object>> updateEvent(@PathVariable int id, @RequestBody String body) {
        Event updatedEvent = eventService.updateEvent(id, body);
        return ResponseEntity.ok(Map.of("Event updated", updatedEvent)); //200
    }

}
