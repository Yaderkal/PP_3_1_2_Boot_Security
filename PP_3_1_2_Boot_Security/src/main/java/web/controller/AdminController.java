package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.Dto.RoleDto;
import web.Dto.UserDto;
import web.Dto.UserRequest;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/api")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }


    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserDto::from)
                .toList();
    }

    @GetMapping("/roles")
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleDto::from)
                .toList();
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null
                ? ResponseEntity.ok(UserDto.from(user))
                : ResponseEntity.notFound().build();
    }


    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequest request) {
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.roleIds()));
        userService.saveUser(request.name(), request.middleName(), request.surName(),
                request.mail(), request.password(), roles);
        User saved = userService.getAllUsers().stream()
                .filter(u -> u.getMail().equals(request.mail()))
                .findFirst()
                .orElseThrow();
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDto.from(saved));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,
                                              @RequestBody UserRequest request) {
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.roleIds()));
        userService.updateUser(id, request.name(), request.middleName(),
                request.surName(), request.mail(), request.password(), roles);
        User updated = userService.getUserById(id);
        return ResponseEntity.ok(UserDto.from(updated));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.removeUserById(id);
        return ResponseEntity.noContent().build();
    }
}
