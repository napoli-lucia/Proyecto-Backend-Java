package service;

import metcamp.backend.eventsmanager.exceptions.ValidationException;
import metcamp.backend.eventsmanager.service.ValidationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationServiceTest {

    private static final ValidationService service = new ValidationService();

    //***TESTS dates***//
    @Test
    @DisplayName("Probando el metodo validateDates con start despues que end")
    void validateDatesTestWithstartDateAfterendDate(){
        //given
        LocalDateTime startDate = LocalDateTime.of(2023,07,02,00,00,00);
        LocalDateTime endDate = LocalDateTime.of(2023,07,01,00,00,00);
        //when
        ValidationException e = assertThrows(ValidationException.class,
                () -> service.validateDates(startDate,endDate));
        //then
        assertEquals("startDate must be before endDate", e.getMessage());
    }

    @Test
    @DisplayName("Probando el metodo validateDates - Happy path")
    void validateDatesTestWithCorrectDate(){
        //given
        LocalDateTime startDate = LocalDateTime.of(2023,07,01,00,00,00);
        LocalDateTime endDate = LocalDateTime.of(2023,07,02,00,00,00);

        assertDoesNotThrow(() -> service.validateDates(startDate,endDate));
    }


}
