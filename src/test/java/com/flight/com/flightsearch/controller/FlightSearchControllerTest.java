package com.flight.com.flightsearch.controller;

import com.flight.com.flightsearch.constant.FlightSearchApplicationConstants;
import com.flight.com.flightsearch.exception.FlightSearchApplicationException;
import com.flight.com.flightsearch.model.Flight;
import com.flight.com.flightsearch.service.FlightSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FlightSearchController.class)
@AutoConfigureMockMvc
public class FlightSearchControllerTest {

    @MockBean
    private FlightSearchService flightSearchService;

    @Autowired
    private MockMvc mvc;
    private Flight flight;
    private List<Flight> flightList = new ArrayList<>();

    @Before
    public void setUp() {
        flight =
                Flight.builder().id(null).flightNumber("E101_test").origin("MAA").destination("BOM").departureTime(LocalTime.parse("11:00")).arrivalTime(LocalTime.parse("16:00"))
                        .price(new BigDecimal(100)).currency(FlightSearchApplicationConstants.CURRENCY_UNIT).build();
        flightList.add(flight);
    }

    @Test
    public void testIfAllFlightsareFetched() throws Exception {
        when(flightSearchService.getAllFlights()).thenReturn(flightList);
        this.mvc.perform(MockMvcRequestBuilders
                        .get("/flight/allFlights")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].flightNumber").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].origin").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].destination").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].departureTime").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].arrivalTime").exists());

    }

    @Test
    public void testIfFlightSearchApplicationExceptionThrownWhileFetchingAllFlights() throws Exception {
        when(flightSearchService.getAllFlights()).thenThrow(new FlightSearchApplicationException("Testing exception for getAllFlights method"));
        this.mvc.perform(MockMvcRequestBuilders
                        .get("/flight/allFlights")
                )
                .andDo(print())
                .andExpect(status().is5xxServerError());

    }

    @Test
    public void testIfFlightNotFoundExceptionThrownWhileFetchingAllFlights() throws Exception {
        when(flightSearchService.getAllFlights()).thenReturn(new ArrayList<Flight>());
        this.mvc.perform(MockMvcRequestBuilders
                        .get("/flight/allFlights")
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testIfFlightsAreSearchableByOriginAndDestination() throws Exception {
        when(flightSearchService.getAllFlightsByOriginAndDestination(anyString(), anyString())).thenReturn(flightList);

        mvc.perform(MockMvcRequestBuilders
                        .get("/flight/search").queryParam("MAA", "BOM")
                        .param("origin", "MAA")
                        .param("destination", "BOM")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testIfFlightSearchApplicationExceptionThrownWhileFetchingAllFlightsByOriginAndDestination() throws Exception {
        when(flightSearchService.getAllFlightsByOriginAndDestination(anyString(), anyString())).thenThrow(new FlightSearchApplicationException("Testing exception for getAllFlightsByOriginAndDestination method"));
        mvc.perform(MockMvcRequestBuilders
                        .get("/flight/search").queryParam("MAA", "BOM")
                        .param("origin", "MAA")
                        .param("destination", "BOM")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is5xxServerError());

    }

    @Test
    public void testIfFlightNotFoundExceptionThrownThrownWhileFetchingAllFlightsByOriginAndDestination() throws Exception {
        when(flightSearchService.getAllFlightsByOriginAndDestination(anyString(), anyString())).thenReturn(new ArrayList<Flight>());
        this.mvc.perform(MockMvcRequestBuilders
                        .get("/flight/search").queryParam("MAA", "BOM")
                        .param("origin", "MAA")
                        .param("destination", "BOM")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }
    @Test
    public void testIfFlightsFetchedByFlightName() throws Exception {
        when(flightSearchService.getByFlightNumber(anyString())).thenReturn(flightList);
        this.mvc.perform(MockMvcRequestBuilders
                        .get("/flight/filterByFlightName").param("flightNumber","E101_test")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void testIfFlightSearchApplicationExceptionThrownWhileFilteringByFlightName() throws Exception {
        when(flightSearchService.getByFlightNumber(anyString())).thenThrow(new FlightSearchApplicationException("Testing exception for getAllFlights method"));
        this.mvc.perform(MockMvcRequestBuilders.get("/flight/filterByFlightName").param("flightNumber","E101_test")
                )
                .andDo(print())
                .andExpect(status().is5xxServerError());

    }

    @Test
    public void testIfFlightNotFoundExceptionThrownWhileFilteringByFlightName() throws Exception {
        when(flightSearchService.getByFlightNumber(anyString())).thenReturn(new ArrayList<Flight>());
        this.mvc.perform(MockMvcRequestBuilders.get("/flight/filterByFlightName").param("flightNumber","E101_test")
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }
    @Test
    public void testIfFlightsFetchedByOrigin() throws Exception {
        when(flightSearchService.getByOrigin(anyString())).thenReturn(flightList);
        this.mvc.perform(MockMvcRequestBuilders
                        .get("/flight/filterByOrigin").param("origin","MAA")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void testIfFlightsFetchedByDestination() throws Exception {
        when(flightSearchService.getByDestination(anyString())).thenReturn(flightList);
        this.mvc.perform(MockMvcRequestBuilders
                        .get("/flight/filterByDestination").param("destination","BOM")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void testIfFlightsFetchedByPrice() throws Exception {
        when(flightSearchService.getByPrice(any())).thenReturn(flightList);
        this.mvc.perform(MockMvcRequestBuilders
                        .get("/flight/filterByPrice").param("price", "100")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
