package com.flight.com.flightsearch.service.impl;

import com.flight.com.flightsearch.dao.FlightRepository;
import com.flight.com.flightsearch.exception.FlightSearchApplicationException;
import com.flight.com.flightsearch.model.Flight;
import com.flight.com.flightsearch.service.FlightSearchService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
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
        List<Flight> flightListByOriginAndDestination;
        try {
            LOGGER.info("Fetching flight details by origin and destination");
            flightListByOriginAndDestination = flightRepository.findFlightByOriginAndDestination(origin, destination).stream().map(flight -> modelMapper.map(flight, Flight.class)).collect(Collectors.toList());
        } catch (Exception exception) {
            LOGGER.error("Failed to get flights by origin and destination");
            throw new FlightSearchApplicationException("Failed to get flights by origin and destination ", exception);
        }
        return flightListByOriginAndDestination;
    }

    @Override
    public List<Flight> getAllFlights() throws FlightSearchApplicationException {
        List<Flight> allFlightList;
        try {
            LOGGER.info("Fetching flight details");
            allFlightList = flightRepository.findAll().stream().map(flight -> modelMapper.map(flight, Flight.class)).collect(Collectors.toList());
        } catch (Exception exception) {
            LOGGER.error("Failed to get all flights");
            throw new FlightSearchApplicationException("Failed to get all flights", exception);
        }
        return allFlightList;
    }

    @Override
    public List<Flight> getAllFlightsSortedBy(String sortingDirection, String fieldName) throws FlightSearchApplicationException {
        List<Flight> allFlightList;
        try {
            LOGGER.info("Fetching flight details");
            if(null!=sortingDirection && sortingDirection.equalsIgnoreCase("ASC")) {
                allFlightList = flightRepository.findAll(Sort.by(Sort.Direction.ASC, fieldName)).stream().map(flight -> modelMapper.map(flight, Flight.class)).collect(Collectors.toList());
            }
            else {
                allFlightList = flightRepository.findAll(Sort.by(Sort.Direction.DESC, fieldName)).stream().map(flight -> modelMapper.map(flight, Flight.class)).collect(Collectors.toList());
            }
        } catch (Exception exception) {
            LOGGER.error("Failed to get all flights");
            throw new FlightSearchApplicationException("Failed to get all flights", exception);
        }
        return allFlightList;
    }

    @Override
    public List<Flight> getByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    @Override
    public List<Flight> getByOrigin(String origin) {
        return flightRepository.findByOrigin(origin);
    }

    @Override
    public List<Flight> getByDestination(String destination) {
        return flightRepository.findByDestination(destination);
    }

    @Override
    public List<Flight> getByDepartureTime(LocalTime departureTime) {
        return flightRepository.findByDepartureTime(departureTime);
    }

    @Override
    public List<Flight> getByArrivalTime(LocalTime arrivalTime) {
        return flightRepository.findByArrivalTime(arrivalTime);
    }

    @Override
    public List<Flight> getByPrice(BigDecimal price) {
        return flightRepository.findByPrice(price);
    }


}
