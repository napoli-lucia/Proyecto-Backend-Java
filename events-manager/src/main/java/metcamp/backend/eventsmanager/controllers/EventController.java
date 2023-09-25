package metcamp.backend.eventsmanager.controllers;

import metcamp.backend.eventsmanager.entities.model.Event;
import metcamp.backend.eventsmanager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/metcamp/backend/events")

public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllEvents(){
        return ResponseEntity.ok(Map.of("events",eventService.getAllEvents())); //200
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Map<String,Object>> getEventById(@PathVariable int id){
        return ResponseEntity.ok(Map.of("event",eventService.getEventById(id))); //200
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createEvent(@Valid @RequestBody Event body){
        return ResponseEntity.status(201).body(Map.of("Event Created",eventService.createEvent(body))); //201
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable int id){
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build(); //204
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Map<String,Object>> updateEvent(@PathVariable int id, @RequestBody Event body) {
        return ResponseEntity.ok(Map.of("Event updated", eventService.updateEvent(id, body))); //200
    }

}
