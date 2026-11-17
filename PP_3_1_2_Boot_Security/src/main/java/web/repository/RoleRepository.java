package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Role;

interface RoleRepository extends JpaRepository<Role, Long> {
}
