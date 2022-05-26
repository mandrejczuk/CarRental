package com.example.projekt.sevices;

import com.example.projekt.models.Reservation;
import com.example.projekt.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService extends GenericManagementService<Reservation, ReservationRepository> {
    public ReservationService(ReservationRepository repository) {
        super(repository);
    }
}
