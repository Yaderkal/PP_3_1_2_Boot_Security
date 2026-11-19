package web.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;
import web.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
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

    /** аналог saveUser(...) из DAO */
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

    /** аналог getUserById(...) */
    @Transactional(readOnly = true)
    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id=" + id + " не найден"));
    }

    /** аналог getAllUsers() */
    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    /** аналог removeUserById(...) */
    @Override
    public void removeUserById(long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Пользователь с id=" + id + " не найден");
        }
        userRepository.deleteById(id);
    }

    /** аналог updateUser(...) */
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
        // save() не обязателен в @Transactional-методе:
        // Hibernate сделает dirty checking и flush при коммите.
        // Но явный save() не вредит и делает намерение очевидным.
        userRepository.save(user);
    }
}