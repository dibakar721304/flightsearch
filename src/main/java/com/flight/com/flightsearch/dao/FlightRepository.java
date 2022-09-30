package com.flight.com.flightsearch.dao;

import com.flight.com.flightsearch.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {


    List<Flight> findFlightByOriginAndDestination(String origin, String destination);

    List<Flight> findByFlightNumber(String flightNumber);

    List<Flight> findByOrigin(String origin);

    List<Flight> findByDestination(String destination);
    List<Flight> findByDepartureTime(LocalTime departureTime);

    List<Flight> findByArrivalTime(LocalTime arrivalTime);

    List<Flight> findByPrice(BigDecimal price);

}
