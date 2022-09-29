package com.flight.com.flightsearch.service.impl;

import com.flight.com.flightsearch.dao.FlightRepository;
import com.flight.com.flightsearch.exception.FlightSearchApplicationException;
import com.flight.com.flightsearch.model.Flight;
import com.flight.com.flightsearch.service.FlightSearchService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightSearchServiceImpl implements FlightSearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightSearchServiceImpl.class);

    @Autowired
    FlightRepository flightRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<Flight> getAllFlightsByOriginAndDestination(String origin, String destination) throws FlightSearchApplicationException {
        List<Flight> flightListByOrginAndDestination;
        try {
            flightListByOrginAndDestination = flightRepository.findFlightByOriginAndDestination(origin, destination).stream().map(flight -> modelMapper.map(flight, Flight.class)).collect(Collectors.toList());
        } catch (Exception exception) {
            LOGGER.info("Failed to get flights by origin and destination");
            throw new FlightSearchApplicationException("Failed to get flights by origin and destination ", exception);
        }
        return flightListByOrginAndDestination;
    }

    @Override
    public List<Flight> getAllFlights() throws FlightSearchApplicationException {
        List<Flight> allFlightList;
        try {
            allFlightList = flightRepository.findAll().stream().map(flight -> modelMapper.map(flight, Flight.class)).collect(Collectors.toList());
        } catch (Exception exception) {
            LOGGER.info("Failed to get all flights");
            throw new FlightSearchApplicationException("Failed to get all flights", exception);
        }
        return allFlightList;
    }
}
