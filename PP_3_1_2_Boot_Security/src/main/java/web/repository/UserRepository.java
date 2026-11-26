package web.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByName(String name);

    Optional<User> findByMail(String mail);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.name = :name")
    Optional<User> findByMailWithRoles(@Param("name") String name);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles")
    List<User> findAllWithRoles();

    Optional<User> findUserByMail(String mail);

}
