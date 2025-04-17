package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entitys.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-page")
    public String userPage(Model model, @AuthenticationPrincipal User loggedUser) {
        if (loggedUser == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(loggedUser.getUsername());
        model.addAttribute("user", user);
        return "user-page";
    }

    @GetMapping("/edit")
    public String editUserPage(Model model, @AuthenticationPrincipal User loggedUser) {
        if (loggedUser == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(loggedUser.getUsername());
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PatchMapping("/edit")
    public String patchUser(@ModelAttribute("user") User user,
                            @AuthenticationPrincipal User loggedUser) {
        if (loggedUser == null || !loggedUser.getId().equals(user.getId())) {
            return "redirect:/login";
        }

        try {
            userService.update(user.getId(), user);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user", e);
        }
        return "redirect:/user/user-page";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @AuthenticationPrincipal User loggedUser) {
        if (loggedUser == null || !loggedUser.getId().equals(user.getId())) {
            return "redirect:/login";
        }
        userService.update(user.getId(), user);
        return "redirect:/user/user-page";
    }
}