package com.example.projekt.controllers;

import com.example.projekt.models.Car;
import com.example.projekt.models.Reservation;
import com.example.projekt.models.User;
import com.example.projekt.repositories.UserRepository;
import com.example.projekt.sevices.CarService;
import com.example.projekt.sevices.ReservationService;
import com.example.projekt.sevices.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {

    private UserServiceImpl userService;

    private CarService carService;

    private ReservationService reservationService;

    private UserRepository userRepository;

    public UserController(UserServiceImpl userService, CarService carService, ReservationService reservationService, UserRepository userRepository) {

        this.userService = userService;
        this.carService = carService;
        this.reservationService = reservationService;
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String home()
    {
        return "home";
    }
    @GetMapping("/sign-up")
    public String singup(Model model)
    {
        model.addAttribute("user", new User());
        String s = "sing-up";
        return "sign-up";
    }

    @PostMapping("/register")
    public String register(User user)
    {
        System.out.println(user);
        userService.registerUser(user);
        String s = "login";
        return s;
    }
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/offers/show/all")
    public String getOffers(Model model)
    {
        model.addAttribute("cars",carService.getAll());

        return "offers";
    }

    @GetMapping("/reservation/{id}")
    public String reservationShowAddForm(@PathVariable("id")Long id, Model model, Principal principal)
    {
        Car car = carService.show(id);
        String username = principal.getName();
        User user = userService.loadUserByEmail(username);
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setCar(car);
        model.addAttribute("reservation", reservation);
        model.addAttribute("car", car);
        model.addAttribute("user", user);
        return "rent";
    }

    @PostMapping("/reservation/add")
    public String addReservation(Reservation reservation)
    {

        reservationService.add(reservation);

        return "redirect:/home";
    }
}
