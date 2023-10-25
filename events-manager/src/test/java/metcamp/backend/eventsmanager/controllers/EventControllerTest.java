package metcamp.backend.eventsmanager.controllers;

import metcamp.backend.eventsmanager.entities.model.Event;
import metcamp.backend.eventsmanager.exceptions.EventAlreadyExistsException;
import metcamp.backend.eventsmanager.exceptions.EventNotFoundException;
import metcamp.backend.eventsmanager.exceptions.ValidationException;
import metcamp.backend.eventsmanager.service.EventService;
import metcamp.backend.eventsmanager.testutils.EventTestUtils;
import metcamp.backend.eventsmanager.utils.MapperUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static metcamp.backend.eventsmanager.testutils.EventTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(EventController.class)
public class EventControllerTest {

    private static final String BASE_URL = "/metcamp/backend/events";

    private final MapperUtils utils = new MapperUtils();

    @MockBean
    private EventService eventService;

    @Autowired
    private MockMvc mockMvc;

    //**Tests de GET**//
    @ParameterizedTest(name = "{index} GET all - {0}")
    @MethodSource("inputForGetAllEvents")
    void testGetAllEvents(ArrayList<Event> eventList) throws Exception {
        when(eventService.getAllEvents()).thenReturn(eventList);

        MockHttpServletRequestBuilder request = get(BASE_URL);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(String.format("{\"events\":%s}", utils.mapToJson(eventList))));
    }


    static Stream<Arguments> inputForGetAllEvents() {
        return Stream.of(
                Arguments.of(Named.of("empty", new ArrayList<>())),
                Arguments.of(Named.of("one element", new ArrayList<>(List.of(validFreeEvent)))),
                Arguments.of(Named.of("multiple elements", new ArrayList<>(List.of(validFreeEvent,validPricedEvent))))
        );
    }

    @Test
    @DisplayName("testing GET by id")
    void testGetEventById() throws Exception {
        when(eventService.getEventById(1)).thenReturn(validFreeEvent)
                                          .thenThrow(new EventNotFoundException("Event 1 doesn't exists"));

        MockHttpServletRequestBuilder request = get(String.format("%s/%s", BASE_URL, 1));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(String.format("{\"event\":%s}", utils.mapToJson(validFreeEvent))));

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"httpStatus\":\"NOT_FOUND\",\"message\":\"Event 1 doesn't exists\"}"));

    }

    //**Tests de POST**//
    @ParameterizedTest(name = "{index} POST OK - {0}")
    @MethodSource("inputForPostEventOK")
    void testPostEventOk(String body) throws Exception {
        Event inputEvent = utils.mapToEvent(body);
        Event expectedEvent = utils.mapToEvent(body);

        when(eventService.createEvent(any(Event.class))).thenReturn(expectedEvent);

        MockHttpServletRequestBuilder request = post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(String.format("{\"Event Created\":%s}", utils.mapToJson(expectedEvent))));

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventService).createEvent(eventCaptor.capture());
        assertThat(eventCaptor.getValue()).usingRecursiveComparison().isEqualTo(inputEvent);
    }


    static Stream<Arguments> inputForPostEventOK() {
        return Stream.of(
                Arguments.of(Named.of("Free event", freeEventJson)),
                Arguments.of(Named.of("Priced event", pricedEventJson)),
                Arguments.of(Named.of("Two priced event", twoPricedEventJson))
        );
    }

    @ParameterizedTest(name = "{index} POST Bad Request - {1}")
    @MethodSource("inputForPostEventBadRequest")
    void testPostEventBadRequest(String body, String expectedMessage) throws Exception {
        when(eventService.createEvent(any(Event.class)))
                .thenThrow(new ValidationException("startDate must be before endDate"));
                //.thenThrow(new EventAlreadyExistsException("Event already exists"));

        MockHttpServletRequestBuilder request = post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json(String.format("{\"httpStatus\":\"BAD_REQUEST\",\"message\":\"%s\"}", expectedMessage)));
    }


    static Stream<Arguments> inputForPostEventBadRequest() {
        return Stream.of(

                //Event type
                Arguments.of(String.format("{\"id\":1,\"name\":\"Met Lab Web\",\"attendees\":100,\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedStart(), EventTestUtils.getFormattedEnd()), "eventType is required: ANIVERSARIO, CLASE_METCAMP, ENCUENTRO_METLAB"),
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"something\",\"name\":\"Met Lab Web\",\"attendees\":100,\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedStart(), EventTestUtils.getFormattedEnd()), "eventType is not valid, must be one of these: ANIVERSARIO, CLASE_METCAMP, ENCUENTRO_METLAB"),
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"\",\"name\":\"Met Lab Web\",\"attendees\":100,\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedStart(), EventTestUtils.getFormattedEnd()), "eventType is not valid, must be one of these: ANIVERSARIO, CLASE_METCAMP, ENCUENTRO_METLAB"),

                //Name
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"attendees\":100,\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedStart(), EventTestUtils.getFormattedEnd()), "name is required"),
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"     \",\"attendees\":100,\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedStart(), EventTestUtils.getFormattedEnd()), "name is required"),
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"abc\",\"attendees\":100,\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedStart(), EventTestUtils.getFormattedEnd()), "name is too short"),
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"abcdefghijklmnop\",\"attendees\":100,\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedStart(), EventTestUtils.getFormattedEnd()), "name is too long"),

                //Attendees
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"Met Lab Web\",\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedStart(), EventTestUtils.getFormattedEnd()), "attendees is required"),
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"Met Lab Web\",\"attendees\":-1,\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedStart(), EventTestUtils.getFormattedEnd()), "attendees must be positive and not zero"),

                //Organizer name
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"Met Lab Web\",\"attendees\":100,\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedStart(), EventTestUtils.getFormattedEnd()), "organizer name is required"),
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"Met Lab Web\",\"attendees\":100,\"organizer\":\"    \",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedStart(), EventTestUtils.getFormattedEnd()), "organizer name is required"),
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"Met Lab Web\",\"attendees\":100,\"organizer\":\"ab\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedStart(), EventTestUtils.getFormattedEnd()), "organizer name is too short"),
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"Met Lab Web\",\"attendees\":100,\"organizer\":\"abcdefghijkl\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedStart(), EventTestUtils.getFormattedEnd()), "organizer name is too long"),

                //Dates
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"Met Lab Web\",\"attendees\":100,\"organizer\":\"MeT\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedEnd()), "startDate is required"),
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"Met Lab Web\",\"attendees\":100,\"organizer\":\"MeT\",\"start_date\":\"%s\"}", EventTestUtils.getFormattedStart()), "endDate is required"),
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"Met Lab Web\",\"attendees\":100,\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedPast(), EventTestUtils.getFormattedEnd()), "startDate must be in the future"),
                Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"Met Lab Web\",\"attendees\":100,\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedStart(), EventTestUtils.getFormattedPast()), "endDate must be in the future")
                //Arguments.of(String.format("{\"id\":1,\"eventType\":\"ENCUENTRO_METLAB\",\"name\":\"Met Lab Web\",\"attendees\":100,\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", EventTestUtils.getFormattedEnd(), EventTestUtils.getFormattedStart()), "startDate must be before endDate")
        );
    }

}
