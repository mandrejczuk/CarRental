package com.example.projekt.sevices;

import com.example.projekt.models.Car;
import com.example.projekt.models.Reservation;
import com.example.projekt.repositories.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarService extends GenericManagementService<Car, CarRepository> {
    public CarService(CarRepository repository) {
        super(repository);
    }

    private Logger logger = LoggerFactory.getLogger(CarService.class);
    public boolean carAvailable(Reservation reservation)
    {

        List<Reservation> reservations = repository.getOne(reservation.getCar().getId()).getReservations();

        for (Reservation r: reservations)
        {
            if(r.getStartDate().isEqual(reservation.getStartDate()))
            {
                logger.warn("User"+ SecurityContextHolder.getContext().getAuthentication().getName() + " has provided wrong date");
                return false;
            }
            else if(r.getStartDate().isEqual(reservation.getEndDate()))
            {
                logger.warn("User"+ SecurityContextHolder.getContext().getAuthentication().getName() + " has provided wrong date");
                return false;
            }
            else if(r.getEndDate().isEqual(reservation.getStartDate()))
            {
                logger.warn("User"+ SecurityContextHolder.getContext().getAuthentication().getName() + " has provided wrong date");
                return false;
            }
            else if(r.getEndDate().isEqual(reservation.getEndDate()))
            {
                logger.warn("User"+ SecurityContextHolder.getContext().getAuthentication().getName() + " has provided wrong date");
                return false;
            }
            else if(r.getStartDate().isBefore(reservation.getStartDate()) && r.getEndDate().isAfter(reservation.getStartDate()))
            {
                logger.warn("User"+ SecurityContextHolder.getContext().getAuthentication().getName() + " has provided wrong date");
                return false;
            }
            else if(r.getStartDate().isBefore(reservation.getEndDate()) && r.getEndDate().isAfter(reservation.getEndDate()))
            {
                logger.warn("User"+ SecurityContextHolder.getContext().getAuthentication().getName() + " has provided wrong date");
                return false;
            }
        }

        return true;
    }
}
