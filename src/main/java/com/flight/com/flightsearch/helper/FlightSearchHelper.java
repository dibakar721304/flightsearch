package com.flight.com.flightsearch.helper;

import com.flight.com.flightsearch.constant.FlightSearchApplicationConstants;
import com.flight.com.flightsearch.exception.FlightSearchApplicationException;
import com.flight.com.flightsearch.model.Flight;
import com.flight.com.flightsearch.model.Price;

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

    public static List<Flight> readFlightDetailsFromCSV() throws FlightSearchApplicationException {
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

        } catch (IOException ioe) {
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
