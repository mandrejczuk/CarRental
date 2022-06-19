package com.example.projekt.sevices;

import com.example.projekt.models.Car;
import com.example.projekt.models.Reservation;
import com.example.projekt.repositories.CarRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarService extends GenericManagementService<Car, CarRepository> {
    public CarService(CarRepository repository) {
        super(repository);
    }

    public boolean carAvailable(Reservation reservation)
    {

        List<Reservation> reservations = repository.getOne(reservation.getCar().getId()).getReservations();

        for (Reservation r: reservations)
        {
            if(r.getStartDate().isEqual(reservation.getStartDate()))
            {
                return false;
            }
            else if(r.getStartDate().isEqual(reservation.getEndDate()))
            {
                return false;
            }
            else if(r.getEndDate().isEqual(reservation.getStartDate()))
            {
                return false;
            }
            else if(r.getEndDate().isEqual(reservation.getEndDate()))
            {
                return false;
            }
            else if(r.getStartDate().isBefore(reservation.getStartDate()) && r.getEndDate().isAfter(reservation.getStartDate()))
            {
                return false;
            }
            else if(r.getStartDate().isBefore(reservation.getEndDate()) && r.getEndDate().isAfter(reservation.getEndDate()))
            {
                return false;
            }
        }

        return true;
    }
}
