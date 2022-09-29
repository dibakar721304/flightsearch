package com.flight.com.flightsearch.controller;

import com.flight.com.flightsearch.exception.FlightNotFoundException;
import com.flight.com.flightsearch.exception.FlightSearchApplicationException;
import com.flight.com.flightsearch.model.Flight;
import com.flight.com.flightsearch.service.FlightSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/flight")
@Validated
@Slf4j
@Tag(description = "Flight search resources that provides access to available flight data",
        name = "Flight search Resource")
public class FlightSearchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightSearchController.class);

    @Autowired
    FlightSearchService flightSearchService;

    @Operation(summary = "Get flight details by origin and destination",
            description = "Get flight details by origin and destination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "${api.response-codes.ok.desc}"),
            @ApiResponse(responseCode = "400",
                    description = "${api.response-codes.badRequest.desc}",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode = "404",
                    description = "${api.response-codes.notFound.desc}",
                    content = {@Content(examples = {@ExampleObject(value = "")})})})
    @GetMapping(value = "/search")
    public ResponseEntity<List<Flight>> getAllFlightsByOriginAndDestination(@NotBlank @NotEmpty @NotNull @Valid @RequestParam String origin, @NotBlank @NotEmpty @NotNull @Valid @RequestParam String destination) {
        List<Flight> flightListByOriginAndDestination;
        try {
            LOGGER.info(String.format("Fetching flights from origin %s, destination %s", origin, destination));
            flightListByOriginAndDestination = flightSearchService.getAllFlightsByOriginAndDestination(origin, destination);
        } catch (FlightSearchApplicationException flightSearchApplicationException) {
            LOGGER.error(String.format("Failed to get flights from origin %s, destination %s", origin, destination));
            throw new FlightSearchApplicationException(String.format("Failed to get flights from origin %s, destination %s", origin, destination), flightSearchApplicationException);
        }
        if (flightListByOriginAndDestination.size() == 0) {
            LOGGER.error(String.format("There are no flights from origin %s, destination %s", origin, destination));
            throw new FlightNotFoundException(String.format("There are no flights from origin %s, destination %s", origin, destination));
        }
        return ResponseEntity.ok(flightListByOriginAndDestination);

    }

    @Operation(summary = "Get flight details",
            description = "Get flight details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "${api.response-codes.ok.desc}"),
            @ApiResponse(responseCode = "400",
                    description = "${api.response-codes.badRequest.desc}",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode = "404",
                    description = "${api.response-codes.notFound.desc}",
                    content = {@Content(examples = {@ExampleObject(value = "")})})})
    @GetMapping(value = "/allFlights")
    public ResponseEntity<List<Flight>> getAllFlights() throws FlightSearchApplicationException, FlightNotFoundException {
        List<Flight> allFlightList;
        try {
            LOGGER.info("Fetching flight details");
            allFlightList = flightSearchService.getAllFlights();
        } catch (FlightSearchApplicationException flightSearchApplicationException) {
            LOGGER.error("Failed to get flights");
            throw new FlightSearchApplicationException("Failed to get flights ", flightSearchApplicationException);
        }
        if (allFlightList.size() == 0) {
            LOGGER.error("There are no flights available");
            throw new FlightNotFoundException("There are no flights available");
        }
        return ResponseEntity.ok(allFlightList);
    }
}
