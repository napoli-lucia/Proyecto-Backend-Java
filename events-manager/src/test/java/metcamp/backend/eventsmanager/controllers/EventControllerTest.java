package metcamp.backend.eventsmanager.controllers;

import metcamp.backend.eventsmanager.service.EventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(EventController.class)
public class EventControllerTest {

    @MockBean
    private EventService eventService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should return 200 and empty list")
    void testEmptyList() throws Exception {
        when(eventService.getAllEvents()).thenReturn(new ArrayList<>());

        MockHttpServletRequestBuilder request = get("/metcamp/backend/events");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"events\":[]}"));
    }


}
