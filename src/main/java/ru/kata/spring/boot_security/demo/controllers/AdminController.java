package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("titleTable", "Список всех пользователей:");
        return "admin";
    }

    @GetMapping("/{id}")
    public String showUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("titleTable", "Страница пользователя ");
        return "user";
    }

    @GetMapping("/new")
    public String addNewUser(Model model, @ModelAttribute("user") User user) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("userRoles", roles);
        return "new";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/editUser")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUser(id));
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("rolesAdd", roles);
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
