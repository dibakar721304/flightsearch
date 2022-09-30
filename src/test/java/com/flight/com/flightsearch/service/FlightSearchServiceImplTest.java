package com.flight.com.flightsearch.service;

import com.flight.com.flightsearch.constant.FlightSearchApplicationConstants;
import com.flight.com.flightsearch.dao.FlightRepository;
import com.flight.com.flightsearch.exception.FlightSearchApplicationException;
import com.flight.com.flightsearch.model.Flight;
import com.flight.com.flightsearch.service.impl.FlightSearchServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {FlightSearchServiceImpl.class})
public class FlightSearchServiceImplTest {
    @Autowired
    private FlightSearchService flightSearchService;
    @MockBean
    private FlightRepository flightRepository;
    @MockBean
    ModelMapper modelMapper;
    private Flight flight;
    private List<Flight> flightList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        flight = Flight.builder().id(null).flightNumber("E101_test").origin("MAA").destination("BOM").departureTime(LocalTime.parse("11:00")).arrivalTime(LocalTime.parse("16:00"))
                .price(new BigDecimal(100)).currency(FlightSearchApplicationConstants.CURRENCY_UNIT).build();
        flightList.add(flight);
        flightRepository.saveAll(flightList);
    }

    @Test
    void testIfFlightsAreSearchableByOriginAndDestinaton() {
        when(flightRepository.findFlightByOriginAndDestination(anyString(), anyString())).thenReturn(flightList);
        assertThat(flightSearchService.getAllFlightsByOriginAndDestination("MAA", "BOM")).isNotNull();
        Assertions.assertEquals(flightSearchService.getAllFlightsByOriginAndDestination("MAA", "BOM").size(), 1);
    }

    @Test
    void testIfAllFlightDetailsAreFetched() {
        when(flightRepository.findAll()).thenReturn(flightList);
        assertThat(flightSearchService.getAllFlights()).isNotNull();
        Assertions.assertEquals(flightSearchService.getAllFlights().size(), 1);
    }

    @Test
    void testIfExceptionThrownWhileFetchingAllFlightsByOriginAndDestination() {
        when(flightRepository.findFlightByOriginAndDestination(anyString(), anyString()))
                .thenThrow(new FlightSearchApplicationException(""));
        Exception exception = assertThrows(FlightSearchApplicationException.class, () -> {
            flightSearchService.getAllFlightsByOriginAndDestination("MAA", "BOM");
        });

        String expectedMessage = "Failed to get flights by origin and destination";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testIfExceptionThrownWhileFetchingAllFlights() {
        when(flightRepository.findAll())
                .thenThrow(new FlightSearchApplicationException(""));
        Exception exception = assertThrows(FlightSearchApplicationException.class, () -> {
            flightSearchService.getAllFlights();
        });

        String expectedMessage = "Failed to get all flights";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void testIfAllFlightDetailsAreFetchedFilteredByFlightNumber() {
        when(flightRepository.findByFlightNumber(anyString())).thenReturn(flightList);
        assertThat(flightSearchService.getByFlightNumber("E101_test")).isNotNull();
        Assertions.assertEquals(flightSearchService.getByFlightNumber("E101_test").size(), 1);
    }
    @Test
    void testIfAllFlightDetailsAreFetchedFilteredByOrigin() {
        when(flightRepository.findByOrigin(anyString())).thenReturn(flightList);
        assertThat(flightSearchService.getByOrigin("MAA")).isNotNull();
        Assertions.assertEquals(flightSearchService.getByOrigin("MAA").size(), 1);
    }
    @Test
    void testIfAllFlightDetailsAreFetchedFilteredByDestination() {
        when(flightRepository.findByDestination(anyString())).thenReturn(flightList);
        assertThat(flightSearchService.getByDestination("BOM")).isNotNull();
        Assertions.assertEquals(flightSearchService.getByDestination("BOM").size(), 1);
    }
    @Test
    void testIfAllFlightDetailsAreFetchedFilteredByArrivalTime() {
        when(flightRepository.findByArrivalTime(any())).thenReturn(flightList);
        assertThat(flightSearchService.getByArrivalTime(LocalTime.of(16,00))).isNotNull();
        Assertions.assertEquals(flightSearchService.getByArrivalTime(LocalTime.of(16,00)).size(), 1);
    }
    @Test
    void testIfAllFlightDetailsAreFetchedFilteredByDepartureTime() {
        when(flightRepository.findByDepartureTime(any())).thenReturn(flightList);
        assertThat(flightSearchService.getByDepartureTime(LocalTime.of(11,00))).isNotNull();
        Assertions.assertEquals(flightSearchService.getByDepartureTime(LocalTime.of(11,00)).size(), 1);
    }
    @Test
    void testIfAllFlightDetailsAreFetchedFilteredByPrice() {
        when(flightRepository.findByPrice(any())).thenReturn(flightList);
        assertThat(flightSearchService.getByPrice(new BigDecimal(100))).isNotNull();
        Assertions.assertEquals(flightSearchService.getByPrice(new BigDecimal(100)).size(), 1);
    }
}