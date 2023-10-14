package metcamp.backend.eventsmanager.entities.model;

import lombok.*;
import metcamp.backend.eventsmanager.validation.ValidateEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Price {

    @NotNull(message = "ticketType is required")
    @ValidateEnum(enumClass = TicketType.class, message = "tickeType must be one of these: REGULAR_FULL_PASS, REGULAR_ONE_DAY, VIP_FULL_PASS, VIP_ONE_DAY")
    private String type;

    @NotNull(message = "currency is required")
    @ValidateEnum(enumClass = Currency.class, message = "currency must be one of these: ARS,CLP,COP,USD")
    private String currency;


    @NotNull(message = "price value is required")
    @Positive(message = "price value must be positive and not zero")
    private Double value;
}
