package com.flight.com.flightsearch.service;

import com.flight.com.flightsearch.model.Flight;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Service
public interface FlightSearchService {
    /**
     * This method returns all the flights from orgin to destination
     * @param source
     * @param destination
     * @return
     */
    List<Flight> getAllFlightsByOriginAndDestination(String source, String destination);
    /**
     * This method returns all the flight details available
     */
    List<Flight> getAllFlights();
    /**
     * This method returns all the flight details available
     */
    List<Flight> getAllFlightsSortedBy(String sortingDirection, String fieldName);
    List<Flight> getByFlightNumber(String FlightNumber);
    List<Flight> getByOrigin(String origin);

    List<Flight> getByDestination(String destination);
    List<Flight> getByDepartureTime(LocalTime departureTime);

    List<Flight> getByArrivalTime(LocalTime arrivalTime);

    List<Flight> getByPrice(BigDecimal price);

}
