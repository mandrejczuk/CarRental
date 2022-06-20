package com.example.projekt.controllers;

import com.example.projekt.models.Car;
import com.example.projekt.models.Reservation;
import com.example.projekt.models.Token;
import com.example.projekt.models.User;
import com.example.projekt.repositories.TokenRepository;
import com.example.projekt.sevices.CarService;
import com.example.projekt.sevices.ReservationService;
import com.example.projekt.sevices.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class ReservationController {

    private ReservationService reservationService;
    private UserServiceImpl userService;
    private CarService carService;
    private TokenRepository tokenRepository;

    public ReservationController(ReservationService reservationService, UserServiceImpl userService, CarService carService, TokenRepository tokenRepository) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.carService = carService;
        this.tokenRepository = tokenRepository;
    }
//RESERVATION ADMIN PANEL

    @GetMapping("/admin/dashboard/reservations/show/all")
    public String getAllReservations(Model model)
    {

        model.addAttribute("reservations",reservationService.getAll());

        return "reservations-admin-panel";
    }
    @GetMapping("/admin/dashboard/reservations/create")
    public String reservationShowCreateForm(Model model)
    {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("users",userService.getAll());
        return "add-reservation-admin-panel";
    }

    @PostMapping("/admin/dashboard/reservations/save")
    public String saveReservation(Reservation reservation)
    {
        reservationService.add(reservation);

        return "redirect:/admin/dashboard/reservations/show/all";
    }
    @GetMapping("/admin/dashboard/reservations/delete/{id}")
    public String reservationDelete(@PathVariable("id") Long id)
    {

        reservationService.adminDeletesReservation(id);
        return "redirect:/admin/dashboard/reservations/show/all";
    }
    @GetMapping("/admin/dashboard/reservations/edit/{id}")
    public String reservationsShowEditForm(@PathVariable ("id")Long id,Model model)
    {
        Reservation reservation = reservationService.show(id);
        model.addAttribute("reservation",reservation);
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("users",userService.getAll());
        return "edit-reservation-admin-panel";
    }
    // RESERVATION USER ACTIONS

    @GetMapping("/user/reservations")
    public String getAllUserReservations(Model model)
    {
        Long id = userService.loadUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        model.addAttribute("reservations",reservationService.getAllUserReservations(id));
        return "user-reservations";
    }
    @GetMapping("/user/reservations/delete/{id}")
    public String userReservationDelete(@PathVariable("id") Long id)
    {
        if(userService.loadUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId() == reservationService.show(id).getUser().getId() )
        {
            reservationService.delete(id);
        }
        return "redirect:/user/reservations";
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
            if(carService.carAvailable(reservation))
            {
                reservationService.add(reservation);
                return "redirect:/home";
            }
            else
            {
                return  "user-add-reservation-fail";
            }


    }

    @GetMapping("/reservation/token")
    public String tokenActivation(@RequestParam String value, Model model)
    {
        if(tokenRepository.existsByValue(value))
        {
           Token token = tokenRepository.findByValue(value);
            Reservation reservation = token.getReservation();
            reservation.setEnabled(true);
            tokenRepository.delete(token);
            reservationService.add(reservation);
            model.addAttribute("reservation",reservation);
            return "user-reservation-enabled";
        }
        return "user-reservation-enabled-fail";
    }
}
