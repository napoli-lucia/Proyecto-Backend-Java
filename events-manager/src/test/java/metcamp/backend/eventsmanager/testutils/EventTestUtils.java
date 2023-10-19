package metcamp.backend.eventsmanager.testutils;

import metcamp.backend.eventsmanager.entities.model.Event;
import metcamp.backend.eventsmanager.entities.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public class EventTestUtils {

    public static final Event validFreeEvent = new Event(1, "ENCUENTRO_METLAB",
            "Met Lab Web", 10, "Met", List.of(),
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

    public static final Event validPricedEvent = new Event(2, "ANIVERSARIO",
            "Gala Met", 100, "Met",
            List.of(new Price("VIP_ONE_DAY", "ARS", 1100.50)),
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
}
