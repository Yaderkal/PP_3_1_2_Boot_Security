package web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.Dto.UserDto;
import web.repository.UserRepository;
import web.model.User;

@RestController
@RequestMapping("/user/api")
public class UserHomeController {

    private final UserRepository userRepository;

    public UserHomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public UserDto currentUser(Authentication authentication) {
        User user = userRepository.findByMail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDto.from(user);
    }
}