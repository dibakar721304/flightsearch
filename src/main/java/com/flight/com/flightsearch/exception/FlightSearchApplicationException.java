package com.flight.com.flightsearch.exception;

public class FlightSearchApplicationException extends RuntimeException {


        public FlightSearchApplicationException(String message) {
            super(message);
        }

        public FlightSearchApplicationException(String message, Throwable cause) {
            super(message, cause);
        }

}
