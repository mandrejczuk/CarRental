package com.example.projekt.sevices;

import com.example.projekt.models.Reservation;
import com.example.projekt.repositories.ReservationRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService extends GenericManagementService<Reservation, ReservationRepository> {
    public ReservationService(ReservationRepository repository) {
        super(repository);
    }

    public List<Reservation> getAllUserReservations(Long userId)
    {
        return  repository.findAllByUserId(userId);
    }

}
