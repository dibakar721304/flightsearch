package com.flight.com.flightsearch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "price")

public class Price implements Serializable {
    @Id
    @GeneratedValue(generator = "price_id_generator")
    @SequenceGenerator(
            name = "price_id_generator",
            sequenceName = "price_id_generator",
            initialValue = 20
    )
    @JsonIgnore
    private Long id;
    private BigDecimal price;
    private String currency;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "price")
    private Flight flight;
}
