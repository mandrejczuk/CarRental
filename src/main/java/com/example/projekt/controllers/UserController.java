package com.example.projekt.controllers;

import com.example.projekt.models.User;
import com.example.projekt.sevices.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
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
}
