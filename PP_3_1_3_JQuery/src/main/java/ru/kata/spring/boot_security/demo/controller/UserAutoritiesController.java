package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.security.sasl.AuthenticationException;
import java.security.Principal;


@Controller
public class UserAutoritiesController {

    private final UserService userService;
    @Autowired
    public UserAutoritiesController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String showUser(Model model, Principal principal) throws AuthenticationException {
        User currentUser = userService.findByUsername(principal.getName());
        model.addAttribute("user", userService.show(currentUser.getId()));
        return "user";
    }
}
