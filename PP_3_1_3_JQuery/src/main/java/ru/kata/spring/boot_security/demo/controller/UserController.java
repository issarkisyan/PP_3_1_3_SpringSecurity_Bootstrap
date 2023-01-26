package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.security.sasl.AuthenticationException;
import java.security.Principal;


@Controller
@RequestMapping("templates/admin")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String readList(Model model, Principal principal, User user) throws AuthenticationException {
        User currentUser = userService.findByUsername(principal.getName());
        model.addAttribute("userAdmin", userService.show(currentUser.getId()));
        model.addAttribute("users", userService.readListUsers());
        model.addAttribute("user");
        return "admin/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "admin/show";
    }
/*
    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/new";
    }

    //Это точно неправильно
    @GetMapping()
    public String newUser(@ModelAttribute("user") User user) {
    return "admin/index";
    }
 */
    @PostMapping()
    public String create (@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/templates/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.show(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.update(id, user);
        return "redirect:/templates/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/templates/admin";
    }
}
