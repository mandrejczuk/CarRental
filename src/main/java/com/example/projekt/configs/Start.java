package com.example.projekt.configs;

import com.example.projekt.models.Car;
import com.example.projekt.models.Reservation;
import com.example.projekt.models.User;
import com.example.projekt.repositories.CarRepository;
import com.example.projekt.repositories.ReservationRepository;
import com.example.projekt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Configuration
@ComponentScan(basePackages = "com.example.projekt")
public class Start {


    public Start(UserRepository userRepository, PasswordEncoder passwordEncoder, ReservationRepository reservationRepository, CarRepository carRepository) {

        User user = new User();
        user.setEmail("admin@admin");
        user.setPassword(passwordEncoder.encode("123"));
        user.setRole("ROLE_ADMIN");
        userRepository.save(user);

        User user1 = new User();
        user1.setEmail("user@user");
        user1.setPassword(passwordEncoder.encode("123"));
        user1.setRole("ROLE_USER");
        userRepository.save(user1);

        Car car = new Car();
        car.setAirConditioning(true);
        car.setBrand("Ford");
        car.setCarType(Car.carType.COMBI);
        car.setAutomaticTransmission(true);
        car.setPricePerDay(500L);
        car.setSeatNumber(5L);
        carRepository.save(car);

        Car car1 = new Car();
        car1.setAirConditioning(false);
        car1.setBrand("FSO Polonez");
        car1.setCarType(Car.carType.COUPÉ);
        car1.setAutomaticTransmission(false);
        car1.setPricePerDay(1000L);
        car1.setSeatNumber(5L);
        carRepository.save(car1);

        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2023,10,12));
        reservation.setEndDate(LocalDate.of(2023,12,12));
        reservation.setCar(car);
        reservation.setUser(user);
        reservationRepository.save(reservation);

        Reservation reservation1 = new Reservation();
        reservation1.setStartDate(LocalDate.of(2022,6,20));
        reservation1.setEndDate(LocalDate.of(2022,7,1));
        reservation1.setCar(car1);
        reservation1.setUser(user1);
        reservationRepository.save(reservation1);


    }
}
