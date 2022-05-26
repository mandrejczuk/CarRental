package com.example.projekt.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationDTO {

    Long id;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    Long car;
    Long user;

}
