package com.example.projekt.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
