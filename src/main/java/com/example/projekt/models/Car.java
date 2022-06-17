package com.example.projekt.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private Long seatNumber;
    private String brand;
    private Boolean airConditioning;
    private Boolean automaticTransmission;
    private Long pricePerDay;
    private carType carType;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    public enum carType {
        SEDAN,
        HATCHBACK,
        LITFBACK,
        COUPÉ,
        COMBI,
        PICKUP,
        MINIVAN,
        ROADSTER

    }
}
