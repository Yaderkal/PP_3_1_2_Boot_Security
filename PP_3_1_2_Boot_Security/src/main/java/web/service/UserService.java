package web.service;

import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    @Transactional
    void removeUserById(long id);
    @Transactional
    void saveUser(String name, String middleName, String surName,
                  String mail, String rawPassword, Set<Role> roles);

    @Transactional(readOnly = true)
    User getUserById(long id);
    @Transactional(readOnly = true)
    List<User> getAllUsers();

    @Transactional
    void updateUser(Long id, String name, String middleName,
                    String surName, String mail, String rawPassword,
                    Set<Role> roles);
}
