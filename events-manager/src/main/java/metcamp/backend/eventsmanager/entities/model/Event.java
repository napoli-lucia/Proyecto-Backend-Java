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
import metcamp.backend.eventsmanager.validation.ValidateEnum;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//@ValidateDate
public class Event {

    @Valid

    @NotNull(message = "id is required")
    @Positive(message = "id must be positive and not zero")
    private Integer id;


    @NotNull(message = "eventType is required: ANIVERSARIO, CLASE_METCAMP, ENCUENTRO_METLAB")
    @ValidateEnum(enumClass = EventType.class, message = "eventType is not valid, must be one of these: ANIVERSARIO, CLASE_METCAMP, ENCUENTRO_METLAB")
    private String eventType;


    @NotNull(message = "name is required")
    @NotBlank(message = "name is required")
    @Size(min = 5, message = "name is too short")
    @Size(max = 15, message = "name is too long")
    private String name;


    @NotNull(message = "attendees is required")
    @Positive(message = "attendees must be positive and not zero")
    private Integer attendees;


    @NotNull(message = "organizer name is required")
    @NotBlank(message = "organizer name is required")
    @Size(min = 3, message = "organizer name is too short")
    @Size(max = 10, message = "organizer name is too long")
    private String organizer;


    private List<@Valid Price> prices;

    //@NotNull(message = "startDate is required")
    //@Future(message = "startDate must be in the future")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "start_date")
    private LocalDateTime startDateTime;

    //@NotNull(message = "endDate is required")
    //@Future(message = "endDate must be in the future")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "end_date")
    private LocalDateTime endDateTime;



    public void update(Event newEventData){
        this.eventType = newEventData.getEventType() != null ? newEventData.getEventType() : this.eventType;
        this.name = newEventData.getName() != null ? newEventData.getName() : this.name;
        this.startDateTime = newEventData.getStartDateTime() != null ? newEventData.getStartDateTime() : this.startDateTime;
        this.endDateTime = newEventData.getEndDateTime() != null ? newEventData.getEndDateTime() : this.endDateTime;
        this.attendees = newEventData.getAttendees() != null ? newEventData.getAttendees() : this.attendees;
        this.organizer = newEventData.getOrganizer() != null ? newEventData.getOrganizer() : this.organizer;
        this.prices = newEventData.getPrices() != null ? newEventData.getPrices() : this.prices;
    }

}
