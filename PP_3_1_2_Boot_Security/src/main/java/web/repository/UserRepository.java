package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.User;

interface UserRepository extends JpaRepository<User, Long> {
    User findAllById(Long id);
}
