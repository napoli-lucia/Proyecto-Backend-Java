package metcamp.backend.eventsmanager.entities.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {

    @Valid

    @Positive(message = "id must be positive and not zero")
    private int id;

    private EventType eventType;

    @NotNull(message = "name is required")
    @NotBlank(message = "name is required")
    @Size(min = 5, max = 15, message = "name must be between 5 and 15 long")
    private String name;

    @Positive(message = "attendees must be positive and not zero")
    private int attendees;

    @NotNull(message = "organizer name is required")
    @NotBlank(message = "organizer name is required")
    @Size(min = 5, message = "organizer name is too short")
    @Size(max = 10, message = "organizer name is too long")
    private String organizer;


    private List<Price> prices;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "start_date")
    private LocalDateTime startDateTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "end_date")
    private LocalDateTime endDateTime;

    public void update(Event newEventData){
        this.eventType = newEventData.getEventType();
        this.name = newEventData.getName();
        this.startDateTime = newEventData.getStartDateTime();
        this.endDateTime = newEventData.getEndDateTime();
        this.attendees = newEventData.getAttendees();
        this.organizer = newEventData.getOrganizer();
        this.prices = newEventData.getPrices();
    }

}
