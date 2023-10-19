package metcamp.backend.eventsmanager.controllers;

import metcamp.backend.eventsmanager.entities.model.Event;
import metcamp.backend.eventsmanager.exceptions.EventAlreadyExistsException;
import metcamp.backend.eventsmanager.exceptions.EventNotFoundException;
import metcamp.backend.eventsmanager.service.EventService;
import metcamp.backend.eventsmanager.utils.MapperUtils;
import static metcamp.backend.eventsmanager.testutils.EventTestUtils.validFreeEvent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static metcamp.backend.eventsmanager.testutils.EventTestUtils.validPricedEvent;
import static org.mockito.ArgumentMatchers.any;
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

    /*
    @Test
    @DisplayName("should return 200 and empty list")
    void testEmptyList() throws Exception {
        when(eventService.getAllEvents()).thenReturn(new ArrayList<>());

        MockHttpServletRequestBuilder request = get("/metcamp/backend/events");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"events\":[]}"));
    }
     */

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
    /*
    @ParameterizedTest(name = "{index} POST Bad Request")
    @MethodSource("inputForPostEventBadRequest")
    void testPostEventBadRequest(String body, String expectedMessage) throws Exception {
        when(eventService.createEvent(any(Event.class)))
                .thenThrow(new EventAlreadyExistsException("Event already exists"));

        MockHttpServletRequestBuilder request = post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json(String.format("{\"httpStatus\":\"BAD_REQUEST\",\"message\":\"%s\"}", expectedMessage)));
    }


    static Stream<Arguments> inputForPostEventBadRequest() {
        return Stream.of(

        );
    }

     */

}
