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
      
4) To get ordered flight list:

http://localhost:9999/flight/allFlightsOrderBy?sortingDirection=ASC&fieldName=price ( method GET)

sortingDirection can be ASC or DESC
fieldName can be any field name e.g flightNumber   

5) To get flights by field:
http://localhost:9999/flight/filterByFlightNumber?flightNumber=E201

http://localhost:9999/flight/filterByOrigin?origin=AMS

http://localhost:9999/flight/filterByDestination?destination=BOM

http://localhost:9999/flight/filterByArrivalTime?arrivalTime=19:30

http://localhost:9999/flight/filterByDepartureTime?departureTime=12:00

http://localhost:9999/flight/filterByPrice?price=100

4) To access swagger ui in browser : http://localhost:9999/openapi/swagger-ui/index.html
