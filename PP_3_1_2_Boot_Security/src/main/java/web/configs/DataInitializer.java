package web.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.repository.UserRepository;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Role adminRole = createRoleIfNotExists("ROLE_ADMIN");
        Role userRole  = createRoleIfNotExists("ROLE_USER");

        // (опционально) создать дефолтного админа
        if (userRepository.findByMail("admin@example.com").isEmpty()) {
            User admin = new User(
                    "Admin", "Adminovich", "Adminov",
                    "admin@example.com",
                    passwordEncoder.encode("admin"),
                    Set.of(adminRole, userRole)
            );
            userRepository.save(admin);
        }
    }

    private Role createRoleIfNotExists(String name) {
        return roleRepository.findByName(name)
                .orElseGet(() -> roleRepository.save(new Role(null, name)));
    }
}
