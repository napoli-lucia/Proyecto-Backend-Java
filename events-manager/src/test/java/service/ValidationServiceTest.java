package service;

import metcamp.backend.eventsmanager.entities.model.Price;
import metcamp.backend.eventsmanager.exceptions.ValidationException;
import metcamp.backend.eventsmanager.service.ValidationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationServiceTest {

    private static final ValidationService service = new ValidationService();

    //***TESTS id***//
    @Test
    @DisplayName("Probando el metodo validateId con 0")
    void validateIdTestWithIdZero(){
        int id = 0;
        ValidationException e = assertThrows(ValidationException.class, () -> service.validateId(id));
        assertEquals("id must not be zero", e.getMessage());
    }

    @Test
    @DisplayName("Probando el metodo validateId con -1")
    void validateIdTestWithIdNegative(){
        int id = -1;
        assertThrows(ValidationException.class, () -> service.validateId(id));
    }

    @Test
    @DisplayName("Probando el metodo validateId con 1 - Happy path")
    void validateIdTestWithIdNotZero(){
        int id = 1;
        assertDoesNotThrow(() -> service.validateId(id));
    }


    //***TESTS attendeess***//
    @Test
    @DisplayName("Probando el metodo validateAttendeess con 1 - Happy path")
    void validateAttendeessTestWithCantPositiva(){
        //given
        int cantidad = 1;
        //when
        boolean resultado = service.validateAttendeess(cantidad);
        //then
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Probando el metodo validateAttendeess con -1")
    void validateAttendeessTestWithCantNegativa(){
        //given
        int cantidad = -1;
        //when
        boolean resultado = service.validateAttendeess(cantidad);
        //then
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Probando el metodo validateAttendeess con 0")
    void validateAttendeessTestWithCantZero(){
        //given
        int cantidad = 0;
        //when
        boolean resultado = service.validateAttendeess(cantidad);
        //then
        assertFalse(resultado);
    }


    //***TESTS names***//
    @Test
    @DisplayName("Probando el metodo validateNames - Happy path")
    void validateNameTestWithNombreCorrecto(){
        //given
        String name = "Presentacion";
        //then
        assertDoesNotThrow(() -> service.validateName(name));
    }

    @Test
    @DisplayName("Probando el metodo validateNames sin nombre")
    void validateNameTestWithNoName(){
        //given
        String name = "";
        //when
        ValidationException e = assertThrows(ValidationException.class, () -> service.validateName(name));
        //then
        assertEquals("name is required", e.getMessage());
    }

    @Test
    @DisplayName("Probando el metodo validateNames nombre corto")
    void validateNameTestWithShortName(){
        //given
        String name = "abc";
        //when
        ValidationException e = assertThrows(ValidationException.class, () -> service.validateName(name));
        //then
        assertEquals("name is too short", e.getMessage());
    }


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


    //***TESTS organizer***//
    @Test
    @DisplayName("Probando el metodo validateOrganizer - Happy path")
    void validateOrganizerTestWithNombreCorrecto(){
        //given
        String org = "MeT";
        //then
        assertDoesNotThrow(() -> service.validateOrganizer(org));
    }

    @Test
    @DisplayName("Probando el metodo validateOrganizer sin nombre")
    void validateOrganizerTestWithNoName(){
        //given
        String org = "";
        //when
        ValidationException e = assertThrows(ValidationException.class, () -> service.validateOrganizer(org));
        //then
        assertEquals("name is required", e.getMessage());
    }

    @Test
    @DisplayName("Probando el metodo validateOrganizer sin nombre")
    void validateOrganizerTestWithBlankName(){
        //given
        String org = "    ";
        //when
        ValidationException e = assertThrows(ValidationException.class, () -> service.validateOrganizer(org));
        //then
        assertEquals("name is required", e.getMessage());
    }

    @Test
    @DisplayName("Probando el metodo validateOrganizer nombre corto")
    void validateOrganizerTestWithShortName(){
        //given
        String org = "ab";
        //when
        ValidationException e = assertThrows(ValidationException.class, () -> service.validateOrganizer(org));
        //then
        assertEquals("name is too short", e.getMessage());
    }

    @Test
    @DisplayName("Probando el metodo validateOrganizer nombre corto")
    void validateOrganizerTestWithLongName(){
        //given
        String org = "qwertyuiopasdfghjklzxcvbnm";
        //when
        ValidationException e = assertThrows(ValidationException.class, () -> service.validateOrganizer(org));
        //then
        assertEquals("name is too long", e.getMessage());
    }

}
