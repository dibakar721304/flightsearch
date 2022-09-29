package com.flight.com.flightsearch.helper;

import com.flight.com.flightsearch.constant.FlightSearchApplicationConstants;
import com.flight.com.flightsearch.exception.FlightSearchApplicationException;
import com.flight.com.flightsearch.model.Flight;
import com.flight.com.flightsearch.model.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FlightSearchHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightSearchHelper.class);

    public static List<Flight> readFlightDetailsFromCSV() throws FlightSearchApplicationException {
        LOGGER.debug("Reading process started for flight details from csv file");
        List<Flight> flights = new ArrayList<>();
        Path pathToFile = Paths.get("flights.csv");
        try (BufferedReader bufferedReader = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] attributes = line.split("\\|");
                Flight flight = createFlightDetails(attributes);
                flights.add(flight);
                line = bufferedReader.readLine();
            }
            LOGGER.debug("Reading process completed for flight details from csv file");
        } catch (IOException ioe) {
            LOGGER.error("Reading process failed for flight details from csv file");
            ioe.printStackTrace();
        }

        return flights;
    }

    private static Flight createFlightDetails(String[] metadata) {

        return Flight.builder().id(null).flightNumber(metadata[0]).
                origin(metadata[1]).
                destination(metadata[2]).
                departureTime(LocalTime.parse(metadata[3])).
                arrivalTime(LocalTime.parse(metadata[4])).
                price(Price.builder().price(new BigDecimal(metadata[5])).currency(FlightSearchApplicationConstants.CURRENCY_UNIT).build()).build();
    }

}
