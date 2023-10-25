package metcamp.backend.eventsmanager.testutils;

import lombok.Getter;
import metcamp.backend.eventsmanager.entities.model.Event;
import metcamp.backend.eventsmanager.entities.model.Price;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventTestUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //Valid dates
    private static final LocalDateTime startDate = LocalDateTime.now().plusDays(1);
    private static final LocalDateTime endDate = LocalDateTime.now().plusDays(2);
    @Getter
    private static final String formattedStart = FORMATTER.format(startDate);
    @Getter
    private static final String formattedEnd = FORMATTER.format(endDate);

    //Invalid dates
    private static final LocalDateTime pastDate = LocalDateTime.now().minusDays(2);
    @Getter
    private static final String formattedPast = FORMATTER.format(pastDate);



    public static final Event validFreeEvent = new Event(1, "ENCUENTRO_METLAB",
            "Met Lab Web", 100, "Met", List.of(),
            startDate, endDate);

    public static final Event validPricedEvent = new Event(2, "ANIVERSARIO",
            "Gala Met", 500, "Met",
            List.of(new Price("VIP_ONE_DAY", "ARS", 1100.50)),
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));


    public static final String freeEventJson = String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"Met Lab Web\",\"attendees\":100,\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", formattedStart, formattedEnd);

    public static final String pricedEventJson = String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"Met Lab Web\",\"attendees\":100,\"organizer\":\"MeT\",\"prices\":[%s],\"start_date\":\"%s\",\"end_date\":\"%s\"}",
            "{\"type\":\"REGULAR_FULL_PASS\",\"currency\":\"ARS\",\"value\":9000.50}",formattedStart, formattedEnd);

    public static final String twoPricedEventJson = String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"Met Lab Web\",\"attendees\":100,\"organizer\":\"MeT\",\"prices\":[%s],\"start_date\":\"%s\",\"end_date\":\"%s\"}",
            "{\"type\":\"REGULAR_ONE_DAY\",\"currency\":\"ARS\",\"value\":7000},{\"type\":\"VIP_ONE_DAY\",\"currency\":\"ARS\",\"value\":3500.0}",formattedStart, formattedEnd);


}
