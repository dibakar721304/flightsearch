# flightsearch
Backend APIs to search flights.

#project set up
1) Checkout the repository in IDE and do 'mvn clean install' in terminal.
2) Execute the main method of FlightSearchApplication class
3) Access below end points from postman :

   a) To get all flights
   
      http://localhost:9999/flight/allFlights ( method GET)
      
   b) To get flights by origin and destination( for e.g between AMS and BOM)  
   
      http://localhost:9999/flight/search?origin=AMS&destination=BOM  ( method GET)
      
4) To access swagger ui in browser : http://localhost:9999/openapi/swagger-ui/index.html
