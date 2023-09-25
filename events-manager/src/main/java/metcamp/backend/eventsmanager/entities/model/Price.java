package metcamp.backend.eventsmanager.entities.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Price {
    private TicketType type;
    private Currency currency;
    private double value;
}
