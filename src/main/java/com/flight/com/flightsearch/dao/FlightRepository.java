package com.flight.com.flightsearch.dao;

import com.flight.com.flightsearch.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {


    List<Flight> findFlightByOriginAndDestination(String origin, String destination);

}
