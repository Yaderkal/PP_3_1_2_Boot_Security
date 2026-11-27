package web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.User;
import web.repository.UserRepository;

@Controller
public class UserHomeController {

    private final UserRepository userRepository;

    public UserHomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public String userHome(Authentication authentication, Model model) {

        User user = userRepository.findByMail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        model.addAttribute("roles", authentication.getAuthorities());
        return "user";
    }
}