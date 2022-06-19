package com.example.projekt;
import com.example.projekt.models.Reservation;
import com.example.projekt.sevices.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ReservationServiceTests {

    @Autowired
    ReservationService reservationService;

    @Test
    public void addTest()
    {
        Reservation reservation = new Reservation();
        reservationService.add(reservation);
        System.out.println(reservationService.getAll().size());
        assert reservationService.getAll().size() == 3;
        reservationService.delete(reservation.getId());
    }

    @Test
    public void deleteTest()
    {
        Reservation reservation = new Reservation();
        reservationService.add(reservation);
        assert reservationService.getAll().size() == 3;
        reservationService.delete(reservation.getId());
        assert reservationService.getAll().size() == 2;
    }

    @Test
    public void showTest()
    {
        Reservation reservation = reservationService.show(2L);
        assert reservation.getCar().getBrand() == "FSO Polonez";
    }

    @Test
    public void getAllTest()
    {
        List<Reservation> reservations = reservationService.getAll();
        assert reservations.size() == 2;
    }
}
