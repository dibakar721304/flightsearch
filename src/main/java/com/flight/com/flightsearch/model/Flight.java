package com.flight.com.flightsearch.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;


@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(generator = "flight_id_generator")
    @SequenceGenerator(
            name = "flight_id_generator",
            sequenceName = "flight_id_generator",
            initialValue = 10
    )
    private Long id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "price_id", referencedColumnName = "id")
    private BigDecimal price;
    private String currency;

}
