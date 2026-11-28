package web.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;
import web.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(String name, String middleName, String surName,
                         String mail, String rawPassword, Set<Role> roles) {
        User user = new User();
        user.setName(name);
        user.setMiddleName(middleName);
        user.setSurName(surName);
        user.setMail(mail);
        user.setPassword(passwordEncoder.encode(rawPassword)); // ← хешируем здесь
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id=" + id + " не найден"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void removeUserById(long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Пользователь с id=" + id + " не найден");
        }
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(Long id, String name, String middleName,
                           String surName, String mail, String rawPassword,
                           Set<Role> roles) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id=" + id + " не найден"));
        user.setName(name);
        user.setMiddleName(middleName);
        user.setSurName(surName);
        user.setMail(mail);
        if (rawPassword != null && !rawPassword.trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(rawPassword));
        }
        if (roles != null) {
            user.setRoles(roles);
        }
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByMail(String mail) {
        return userRepository.findByMail(mail);
    }
}