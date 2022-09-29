package com.flight.com.flightsearch.config;

import com.flight.com.flightsearch.dao.FlightRepository;
import com.flight.com.flightsearch.exception.FlightSearchApplicationException;
import com.flight.com.flightsearch.helper.FlightSearchHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class FlightSearchInitalDataLoad implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightSearchInitalDataLoad.class);

    @Autowired
    FlightRepository flightRepository;

    @Override
    public void run(ApplicationArguments args) throws FlightSearchApplicationException {
        LOGGER.debug("Process started to save flight objects to database");
        flightRepository.saveAll(FlightSearchHelper.readFlightDetailsFromCSV());
        LOGGER.debug("Process ended to save flight objects to database");
    }

}



