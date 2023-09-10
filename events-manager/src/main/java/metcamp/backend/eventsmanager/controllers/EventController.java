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
        //return ResponseEntity.ok(Map.of("events","[GET Lista de eventos]"));
        //return eventService.getAllEvents();

        ArrayList<Event> temporalList = eventService.getAllEvents();
        return temporalList.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : ResponseEntity.ok(Map.of("events",temporalList));

    }

    @GetMapping("/{id}")
    public ResponseEntity getEventById(@PathVariable int id){
        return ResponseEntity.ok(Map.of("events",String.format("[GET Evento con id %s]", id))) ;
    }


    @PostMapping
    public ResponseEntity createEvent(@RequestBody String body){
        //Todo
        //Ver cuando body esta vacio

        if (body.contains("*")) {
            return ResponseEntity.badRequest().body("No se permiten caracteres especiales");
        } else {
            return ResponseEntity.ok(Map.of("Datos recibidos: ",body));
        }
    }

}
