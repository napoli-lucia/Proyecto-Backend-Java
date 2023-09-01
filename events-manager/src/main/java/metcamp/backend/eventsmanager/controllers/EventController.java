package metcamp.backend.eventsmanager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/metcamp/backend/events")
public class EventController {

    @GetMapping
    public ResponseEntity getAllEvents(){
        return ResponseEntity.ok(Map.of("events","[GET Lista de eventos]"));
    }

    @GetMapping("/{id}")
    public ResponseEntity getEventById(@PathVariable int id){
        return ResponseEntity.ok(Map.of("events",String.format("[GET Evento con id %s]", id))) ;
    }


    @PostMapping
    public ResponseEntity createEvent(@RequestBody String body){
        if (body.contains("*")) {
            return ResponseEntity.badRequest().body("No se permiten caracteres especiales");
        } else {
            return ResponseEntity.ok(Map.of("Datos recibidos: ",body));
        }
    }

}
