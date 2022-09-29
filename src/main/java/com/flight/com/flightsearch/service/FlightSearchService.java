package com.flight.com.flightsearch.service;

import com.flight.com.flightsearch.model.Flight;
import org.springframework.stereotype.Service;

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
}
