package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.repository.RoleRepository;
import web.service.UserService;

@Controller
public class AdminController {
    private UserService userService;
    private RoleRepository roleRepository;
    @Autowired
    public AdminController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/admin/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "user-form";
    }

    @PostMapping("/admin/create")
    public String createUser(@ModelAttribute User user) {
        userService.saveUser(user.getName(), user.getMiddleName(), user.getSurName(),
                user.getMail(), user.getPassword(), user.getRoles());
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleRepository.findAll());
        return "user-edit";
    }

    @PostMapping("/admin/update")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user.getId(), user.getName(), user.getMiddleName(),
                user.getSurName(), user.getMail(), user.getPassword(), user.getRoles());
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}
