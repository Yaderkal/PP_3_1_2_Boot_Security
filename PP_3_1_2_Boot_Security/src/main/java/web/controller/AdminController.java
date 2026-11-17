package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/admin/get")
    public String getUserById(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-details";
    }

    @GetMapping("/admin/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user, Model model) {
        userService.saveUser(user.getName(), user.getMiddleName(), user.getSurName(), user.getMail());
        return "redirect:/users";
    }

    @GetMapping("/admin/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping("/admin/update")
    public String updateUser(@ModelAttribute User user, Model model) {
        userService.updateUser(user.getId(), user.getName(), user.getMiddleName(), user.getSurName(), user.getMail());
        return "redirect:/users";
    }
}
