package com.example.projekt.controllers;

import com.example.projekt.DTO.ReservationDTO;
import com.example.projekt.models.Reservation;
import com.example.projekt.sevices.ReservationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final ModelMapper mapper;

    @GetMapping("/all")
    public List<ReservationDTO> getAll()
    {
        return reservationService.getAll()
                .stream()
                .map(i -> mapper.map(i, ReservationDTO.class))
                .collect(Collectors.toList());
    }
    @DeleteMapping("/delete")
    public void delete(@RequestBody Long id)
    {
        reservationService.delete(id);
    }

    @GetMapping("/show")
    public ReservationDTO show(@RequestBody Long id)
    {
        return mapper.map(reservationService.show(id),ReservationDTO.class);
    }

    @PostMapping("/add")
    public ReservationDTO add(@RequestBody Reservation reservation)
    {
        return mapper.map(reservationService.add(reservation), ReservationDTO.class);
    }


}
