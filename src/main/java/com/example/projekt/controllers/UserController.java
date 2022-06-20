package com.example.projekt.controllers;

import com.example.projekt.models.Car;
import com.example.projekt.models.Reservation;
import com.example.projekt.models.User;
import com.example.projekt.repositories.TokenRepository;
import com.example.projekt.repositories.UserRepository;
import com.example.projekt.sevices.CarService;
import com.example.projekt.sevices.ReservationService;
import com.example.projekt.sevices.UserServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private UserServiceImpl userService;
    private TokenRepository tokenRepository;
    private ReservationService reservationService;

    public UserController(UserServiceImpl userService,TokenRepository tokenRepository,ReservationService reservationService ) {

        this.userService = userService;
        this.tokenRepository = tokenRepository;

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
        return "sign-up";
    }

    @PostMapping("/register")
    public String register(User user)
    {
        if(userService.isEmailUnique(user.getEmail()))
        {
            userService.registerUser(user);
            return "login";
        }
        else
        {
            return "registration-fail";
        }
    }
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }





    // USER PANEL

    @GetMapping("admin/dashboard/users/show/all")
    public String getAllUser(Model model)
    {

        model.addAttribute("users",userService.getAll());

        return "users-admin-panel";
    }
    @GetMapping("admin/dashboard/users/create")
    public String userShowCreateForm(Model model)
    {
        model.addAttribute("user", new User());
        return "add-user-admin-panel";
    }

    @PostMapping("admin/dashboard/users/save")
    public String saveUser(@ModelAttribute User user)
    {
        if(userService.isEmailUnique(user.getEmail()))
        {
            userService.addUser(user);
            return "redirect:/admin/dashboard/users/show/all";
        }
        else
        {
            return "add-user-admin-panel-fail";
        }

    }

    @GetMapping("admin/dashboard/users/edit/{id}")
    public String usersShowEditForm(@PathVariable ("id")Long id,Model model)
    {
        User user = userService.findbyId(id).get();
        model.addAttribute("user",user);
        return "edit-user-admin-panel";
    }
    @GetMapping("admin/dashboard/users/delete/{id}")
    public String usersDelete(@PathVariable ("id") Long id)
    {

        if(SecurityContextHolder.getContext().getAuthentication().getName() == userService.findbyId(id).get().getEmail())
        {
            userService.deleteUserbyId(id);
            return "redirect:/logout";
        }
        else
        {
            List<Reservation> reservations = userService.findbyId(id).get().getReservations();
            for (Reservation r : reservations) {
                if(tokenRepository.existsByReservation(r))
                {
                    tokenRepository.delete(tokenRepository.findByReservation(r));
                }
            }
            userService.deleteUserbyId(id);
            return "redirect:/admin/dashboard/users/show/all";
        }
    }

    @GetMapping("/admin/dashboard")
    public String getDashboard(Principal principal) {

        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
        {
            User user = userService.loadUserByEmail(principal.getName());

            if (user.getRole() == "ROLE_ADMIN") {
                return "admin-panel";
            } else {
                return "home";
            }
        }
        else
        {
            return "login";
        }

    }
}
